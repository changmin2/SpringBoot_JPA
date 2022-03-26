package hi.practicespring.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Board> board = new ArrayList<>();

}
