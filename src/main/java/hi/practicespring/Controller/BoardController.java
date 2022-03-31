package hi.practicespring.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hi.practicespring.Domain.Board;
import hi.practicespring.Domain.Comment;
import hi.practicespring.Domain.Form.CommentForm;
import hi.practicespring.Domain.Form.PolicyForm;
import hi.practicespring.Domain.Form.WriteForm;
import hi.practicespring.Service.BoardService;
import hi.practicespring.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/")
    public String atHome(Model model){

        String url="http://127.0.0.1:5000/moa/policy_api";
        String sb="";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            System.out.println(sb);
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sb = sb.replace("[","");
        sb = sb.replace("]","");
        sb = sb.replace("\"","");
        sb = sb.replace(", ","·");
        String [] array = sb.split(",");
        System.out.println(array[5]);
        model.addAttribute("policy",array);
        return "index";
    }

    @RequestMapping("/board")
    public String home(Model model,@PageableDefault(size = 10) Pageable pageable){
        Page boards = boardService.allFind(pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber()-1);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+2);
        while(endPage-startPage <4 && endPage!=boards.getTotalPages()){
           endPage+=1;
       }
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board";
    }
    @GetMapping("/write")
    public String write(Model model){
        return "write";
    }

    @PostMapping("/write")
    public String write(WriteForm writeForm, Model model){
        Board board = new Board();
        board.setUpdateDate(LocalDate.now());
        board.setTitle(writeForm.getTitle());
        board.setContent(writeForm.getContent());
        board.setHit(0);
        boardService.join(board);
        return  "redirect:/board";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam("pageid") String pageId, Model model){
        Board findBoard = boardService.FidById(Long.parseLong(pageId));
        List<Comment> commnets = findBoard.getCommnets();
        model.addAttribute("comments",commnets);
        model.addAttribute("board",findBoard);
        return "detail";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("pageid") String pageId, Model model){
        boardService.delete(Long.parseLong(pageId));
        return "redirect:/board";
    }

    @RequestMapping("/update")
    public String update(@RequestParam("pageid") String pageId, Model model){
        Board board = boardService.FidById(Long.parseLong(pageId));
        model.addAttribute("board",board);
        return "update";
    }

    @PostMapping("/update")
    public String update(WriteForm writeForm, Model model){
        boardService.update(writeForm);
        return  "redirect:/board";
    }

    @PostMapping("/comment")
    @ResponseBody
    public String comment(@RequestBody Map<String,String> map){
        CommentForm commentForm = new CommentForm();
        String boardid = map.get("boardid");
        String commentid = map.get("commentid");
        String commentpassword =  map.get("commentpassword");
        String content = map.get("content");
        commentForm.setReplyId(commentid);
        commentForm.setReplyPassword(commentpassword);
        commentForm.setReplyContent(content);
        boardService.setComment(commentForm,Long.parseLong(boardid));
        return  "success";
    }

    @PostMapping("/replycomment")
    @ResponseBody
    public String replycomment(@RequestBody Map<String,String> map){
        CommentForm commentForm = new CommentForm();
        String parentid = map.get("id");
        String commentid = map.get("commentid");
        String commentpassword =  map.get("commentpassword");
        String content = map.get("content");
        commentForm.setReplyId(commentid);
        commentForm.setReplyPassword(commentpassword);
        commentForm.setReplyContent(content);
        boardService.setReplyComment(Long.parseLong(parentid),commentForm);
        return  "success";
    }
}
