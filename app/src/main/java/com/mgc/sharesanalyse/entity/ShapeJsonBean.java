package com.mgc.sharesanalyse.entity;

public class ShapeJsonBean {
    private int AllC;
    private int N_SStep;
    private int N_StopPrice;
    private int N_TryPrice;
    private int N_TopPrice;
    private N_Shage n_shage;

    public static class N_Shage {
        private float FFPrice;
        private boolean isFFInit;
        private boolean FFComplete;
        private float ReboundPrice;
        private float SFPrice;
        private boolean isSFInit;
        private boolean SFComplete;
    }
}
