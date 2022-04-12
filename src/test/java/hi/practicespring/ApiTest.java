package hi.practicespring;

import org.junit.Test;
import org.springframework.ui.Model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ApiTest {
    private static String getTagValue(String tag,Element element){
        NodeList nlList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue==null)
            return null;
        return nValue.getNodeValue();
    }
    @Test
    public void ncpms(){
        try{
            //startPoint 는 10개씩 증가 시켜주기 한페이지당 10개보여줬으니 그 다음 열개
            String url="http://ncpms.rda.go.kr/npmsAPI/service?apiKey=2022c2402b14214c42ba4ec44132fff7ec01&serviceCode=SVC01&serviceType=AA001&cropName=배추&displayCount=10&startPoint=1";
            Document documentInfo = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(url);
            documentInfo.getDocumentElement().normalize();
            NodeList nodeList = documentInfo.getElementsByTagName("item");
            System.out.println(nodeList);
            for(int temp = 0;temp<nodeList.getLength();temp++){
                Node nNode = nodeList.item(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    System.out.println("##################");

                    System.out.println("작물명 : " + getTagValue("cropName",eElement));
                    System.out.println("해충이름 : " + getTagValue("sickNameKor",eElement));
                    System.out.println("해충 피해 대표 이미지 : " + getTagValue("thumbImg",eElement));

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
//        String sb="";
//        try{
//            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//
//            String line = null;
//
//            while ((line = br.readLine()) != null) {
//                sb = sb + line + "\n";
//            }
//            System.out.println(sb);
//            br.close();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        System.out.println("BoardController.ncpms"+sb);

    }
}
