package hi.practicespring;

import hi.practicespring.Domain.Form.apiForm;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;


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
        List<apiForm> arr = new ArrayList<>();
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
                    apiForm apiForm = new apiForm();
                    apiForm.setName( getTagValue("cropName",eElement));
                    apiForm.setBugName(getTagValue("sickNameKor",eElement));
                    apiForm.setImgUrl(getTagValue("thumbImg",eElement));
                    arr.add(apiForm);

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
