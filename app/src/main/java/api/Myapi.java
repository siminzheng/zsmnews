package api;

public class Myapi {



    //自己服务器联网请求的ip和端口
    public static final String BASE_URL="http://192.168.100.14:8080/web_home";
    //自己服务器新闻中心的网络地址
    public static final String NEWSCENTER_URL=BASE_URL+"/static/api/news/categories.json";

    //聚合数据的api
    public static  final String JUHEBASE_URL="http://v.juhe.cn/toutiao/index?type=";
    //聚合数据的新闻中心url
    //别人的key:0af60a86bfa3575d53c1491d1be310ca
    //我的key:62b2fe78178e879ae7e9e1ccbeb760e0

    public static  final String JUHENEWS_URL="&page_size=30&is_filter=&key=62b2fe78178e879ae7e9e1ccbeb760e0";




    //网易图片api

    //20.json表示加载20张图片
    //   /0/不用管他，就用/0/就行
    public static String Wangyipic_prebaseurl="http://pic.news.163.com/photocenter/api/list";
    public static String Wangyipic_postbaseurl="/1.json";

    //网易图片api(我的服务器版本)
    //比如:http://192.168.100.14:8080/web_home/static/api/news/xinwen/MyPhotoNews1.json
    public static String Wangyipic_myprebaseurl="http://192.168.100.14:8080/web_home/static/api/news/";
    public static String Wangyipic_mymidbaseurl="/MyPhotoNews";
    public static String Wangyipic_mypostbaseurl=".json";

    // 新闻图片：0001/00AP0001,3R710001,4T8E0001/
    //url:http://pic.news.163.com/photocenter/api/list/0001/00AP0001,3R710001,4T8E0001/0/20.json


    // 热点图片：0001/00AN0001,00AO0001/
    //url:http://pic.news.163.com/photocenter/api/list/0001/00AN0001,00AO0001/0/20.json


    // 明星图片：0003/00AJ0003,0AJQ0003,3LF60003,00B70003,00B50003/
    //url:http://pic.news.163.com/photocenter/api/list/0003/00AJ0003,0AJQ0003,3LF60003,00B70003,00B50003/0/20.json

    //游戏图片
    //url:http://pic.news.163.com/photocenter/api/list/0031/6LRK0031,6LRI0031/0/20.json



    //网易视频api,1-10表示第一条到第十条
    public static String Wangyivideo_Baseurl="http://c.m.163.com/nc/video/list/";
    public static String Wangyivideo_midyurl="/y/";
    public static String Wangyivideo_posturl=".html";
    // 搞笑视屏 http://c.m.163.com/nc/video/list/VAP4BFE3U/y/0-10.html
    // 美女视频 http://c.m.163.com/nc/video/list/VAP4BG6DL/y/0-10.html
    // 新闻现场 http://c.m.163.com/nc/video/list/VAV3H6JSN/y/0-10.html
    // 萌物 http://c.m.163.com/nc/video/list/VAP4BFR16/y/0-10.html
    // 黑科技  http://c.m.163.com/nc/video/list/VBF8F2PKF/y/0-10.html
    // 二次元 http://c.m.163.com/nc/video/list/VBF8F1PSA/y/0-10.html
    // 涨姿势  http://c.m.163.com/nc/video/list/VBF8F3SGL/y/0-10.html




    //极速新闻的api
    public static final String JISUBASE_URL="https://api.jisuapi.com/news/";
    //极速新闻频道url
    public static final String JISUPINDAO_URL=JISUBASE_URL+"channel?appkey=c9edbf66b7bda46d";
    //极速新闻具体频道
    public static final String JISULIBIE_URL=JISUBASE_URL+"get?channel=";
    public static final String JISULIBIEHOUZHUI_URL="&start=0&num=40&appkey=c9edbf66b7bda46d";
//    //极速新闻头条url
//    public static final String JISUTOUTIAO_URL=JISUBASE_URL+"get?channel=头条&start=0&num=40&appkey=c9edbf66b7bda46d";
//    //极速新闻新闻url
//    public static final String JISUTOUNEWS_URL=JISUBASE_URL+"get?channel=新闻&start=0&num=40&appkey=c9edbf66b7bda46d";
//    //极速新闻国内url
//    public static final String JISUGUONEI_URL=JISUBASE_URL+"get?channel=国内&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUGUOJI_URL=JISUBASE_URL+"get?channel=国际&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUZHENGZHI_URL=JISUBASE_URL+"get?channel=政治&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUCAIJING_URL=JISUBASE_URL+"get?channel=财经&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUTIYU_URL=JISUBASE_URL+"get?channel=体育&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUYULE_URL=JISUBASE_URL+"get?channel=娱乐&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUJUNSHI_URL=JISUBASE_URL+"get?channel=军事&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUJIAOYU_URL=JISUBASE_URL+"get?channel=教育&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUKEJI_URL=JISUBASE_URL+"get?channel=科技&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUNBA_URL=JISUBASE_URL+"get?channel=NBA&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUGUPIAO_URL=JISUBASE_URL+"get?channel=股票&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUXINGZUO_URL=JISUBASE_URL+"get?channel=星座&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUNVXING_URL=JISUBASE_URL+"get?channel=女性&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUJIANKANG_URL=JISUBASE_URL+"get?channel=健康&start=0&num=40&appkey=c9edbf66b7bda46d";
//
//    public static final String JISUYUER_URL=JISUBASE_URL+"get?channel=育儿&start=0&num=40&appkey=c9edbf66b7bda46d";


}
