package hi.practicespring.Service;

import hi.practicespring.Domain.Board;
import hi.practicespring.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class BoardService {
    private static final int PAGE_COUNT = 5;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void join(){
        for(int i=0;i<50;i++){
            Board board = new Board();
            board.setTitle("테스트 제목 " + i);
            board.setContent("테스트 내용"+i);
            board.setUpdateDate(LocalDate.now());
            board.setHit(i);
            boardRepository.save(board);
        }
    }
    public Page<Board> allFind(Pageable pageable){
        return boardRepository.findAll(pageable);

    }

}
