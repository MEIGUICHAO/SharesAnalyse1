/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.stetho.inspector.elements.android;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import com.facebook.stetho.inspector.elements.NodeType;
import com.facebook.stetho.inspector.elements.android.window.WindowRootViewCompat;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

final class ApplicationDescriptor extends AbstractChainedDescriptor<Application> {
  private final Map<Application, ElementContext> mElementToContextMap =
      Collections.synchronizedMap(new IdentityHashMap<Application, ElementContext>());

  private final ActivityTracker mActivityTracker = ActivityTracker.get();

  private ElementContext getContext(Application element) {
    return mElementToContextMap.get(element);
  }

  @Override
  protected void onHook(Application element) {
    ElementContext context = new ElementContext();
    context.hook(element);
    mElementToContextMap.put(element, context);
  }

  @Override
  protected void onUnhook(Application element) {
    ElementContext context = mElementToContextMap.remove(element);
    context.unhook();
  }

  @Override
  protected NodeType onGetNodeType(Application element) {
    return NodeType.ELEMENT_NODE;
  }

  @Override
  protected void onGetChildren(Application element, Accumulator<Object> children) {
    ElementContext context = getContext(element);
    List<WeakReference<Activity>> activities = context.getActivitiesList();
    // We report these in reverse order so that the newer ones show up on top
    for (int i = activities.size() - 1; i >= 0; --i) {
      Activity activity = activities.get(i).get();
      if (activity != null) {
        children.store(activity);
      }
    }
    storeWindowIfNeeded(element, children, activities);
  }

  private void storeWindowIfNeeded(Application application, Accumulator<Object> children, List<WeakReference<Activity>> activities) {
    List<View> rootViews = WindowRootViewCompat.get(application).getRootViews();
    for (View view : rootViews) {
      if (!isDecorViewOfActivity(view, activities)) {
        children.store(view);
      }
    }
  }

  private static boolean isDecorViewOfActivity(View view, List<WeakReference<Activity>> references) {
    Util.throwIfNull(references);
    for (WeakReference<Activity> reference : references) {
      Activity activity = reference.get();
      if (activity == null) {
        continue;
      }
      if (activity.getWindow().getDecorView() == view) {
        return true;
      }
    }
    return false;
  }

  private class ElementContext {
    private Application mElement;

    public ElementContext() {
    }

    public void hook(Application element) {
      mElement = element;
      mActivityTracker.registerListener(mListener);
    }

    public void unhook() {
      mActivityTracker.unregisterListener(mListener);
      mElement = null;
    }

    public List<WeakReference<Activity>> getActivitiesList() {
      return mActivityTracker.getActivitiesView();
    }

    private final ActivityTracker.Listener mListener = new ActivityTracker.Listener() {
      @Override
      public void onActivityAdded(Activity activity) {
        // TODO: once we have the ability to report fine-grained updates, do that here
      }

      @Override
      public void onActivityRemoved(Activity activity) {
        // TODO: once we have the ability to report fine-grained updates, do that here
      }
    };
  }
}
