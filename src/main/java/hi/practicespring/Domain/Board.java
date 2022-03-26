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
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private LocalDate updateDate;

    private int hit;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> commnets = new ArrayList<>();
}
