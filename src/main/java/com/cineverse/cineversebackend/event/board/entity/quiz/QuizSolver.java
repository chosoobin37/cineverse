package com.cineverse.cineversebackend.event.board.entity.quiz;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_quiz_solver")
public class QuizSolver {
    @Id
    @Column(name = "quiz_solver_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizSolverId;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "quiz_correct")
    private String quizCorrect;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "quiz_id")
    private Quiz quiz;
}
