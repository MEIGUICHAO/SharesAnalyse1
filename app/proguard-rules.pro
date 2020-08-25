# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#http://hq.sinajs.cn/list=sz002307,sh600928
#0：”大秦铁路”，股票名字；
#1：”27.55″，今日开盘价；
#2：”27.25″，昨日收盘价；
#3：”26.91″，当前价格；
#4：”27.55″，今日最高价；
#5：”26.20″，今日最低价；
#6：”26.91″，竞买价，即“买一”报价；
#7：”26.92″，竞卖价，即“卖一”报价；
#8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
#9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
#10：”4695″，“买一”申请4695股，即47手；
#11：”26.91″，“买一”报价；
#12：”57590″，“买二”
#13：”26.90″，“买二”
#14：”14700″，“买三”
#15：”26.89″，“买三”
#16：”14300″，“买四”
#17：”26.88″，“买四”
#18：”15100″，“买五”
#19：”26.87″，“买五”
#20：”3100″，“卖一”申报3100股，即31手；
#21：”26.92″，“卖一”报价
#(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
#30：”2008-01-11″，日期；
#31：”15:05:32″，时间；


#http://localhost:8080
#adb forward tcp:8080 tcp:8080
#adb connect 127.0.0.1:7555
#adb connect 127.0.0.1:5555

#need ladder
#chrome://inspect/#devices


#600036---n:招商银行,s:190,10ts:1,20ms:9,10ms:42,5ms:108,1000ts:2,100ts:20,curGt:1,o:37.85,c:37.33,p:-1.00%,2020-08-13
#600030: pa_ge50m:(13:41:59,pa:9044.307,cur:31.280,open:31.180,h:31.290,l:30.710,p:.00%,h/l:2.00%,sp:34.289)
#600030: pa_ge50m:(13:43:42,pa:6059.166,cur:31.370,open:31.180,h:31.390,l:30.710,p:1.00%,h/l:2.00%,sp:34.289)


#pa_ge100m pa>100m
#pa_10Last pa>10last
#pa_ge50m  pa>50m
#pa_ge20m  pa>20m
#pa_ge10m  pa>10m
#pa_ge5m   pa>5m
#ps_gt1000times        ps>1000ts
#ps_gt100times         ps>100ts
#pp_perPricesGtCur     pp>cur
#pp_curGtPerPrices     pp<cur
#pa_ge50m:(13:41:59,9044.307,p:.00%)



#logAloneSum
#code@@@
#content+date####
#content2+date####


# ####!!(
# !!(











