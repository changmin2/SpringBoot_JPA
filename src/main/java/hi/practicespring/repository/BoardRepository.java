package hi.practicespring.repository;

import hi.practicespring.Domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
//JPARepository의 첫번째는 엔티티 두번재는 피케이
public interface BoardRepository extends JpaRepository<Board,Long> {

}
