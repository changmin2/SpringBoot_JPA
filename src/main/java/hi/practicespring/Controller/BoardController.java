package hi.practicespring.Controller;

import hi.practicespring.Domain.Board;
import hi.practicespring.Domain.Comment;
import hi.practicespring.Domain.Form.CommentForm;
import hi.practicespring.Domain.Form.WriteForm;
import hi.practicespring.Service.BoardService;
import hi.practicespring.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/")
    public String atHome(Model model){
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
    public String comment(@RequestParam("pageid") String pageId, CommentForm commentForm, Model model){
        System.out.println("BoardController.comment"+commentForm.getReplyContent());
        boardService.setComment(commentForm,Long.parseLong(pageId));
        return  "redirect:/detail?pageid="+pageId;
    }

    @PostMapping("/replycomment")
    @ResponseBody
    public String replycomment(@RequestBody CommentForm param){
        return  "success";
    }
}
