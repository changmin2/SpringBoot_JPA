package hi.practicespring.Controller;

import hi.practicespring.Domain.Board;
import hi.practicespring.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping("/write")
    public String write(Model model){
        return "write";
    }

}
