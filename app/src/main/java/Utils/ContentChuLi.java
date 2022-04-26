package Utils;

public class ContentChuLi {




    public static String Chuli(String htmldata){


        htmldata=htmldata.replaceAll ("\\\\r\\\\n", "<br>");
        htmldata=htmldata.replaceAll("\\[link p4=\\\\","地图为:");
        htmldata=htmldata.replaceAll("\\[link p4=","地图为:");
        htmldata=htmldata.replaceAll("title=\\\\"," ");
        htmldata=htmldata.replaceAll("\"详情\\\\\"\\]详情\\[\\/link\\]","");
        htmldata=htmldata.replaceAll("\\[\\/link\\]"," ");
        htmldata=htmldata.replaceAll("title=\""+"购票详情"+"\"\\]"," ");
        htmldata=htmldata.replaceAll ("小i", "小Z");
        return htmldata;
    }
}
