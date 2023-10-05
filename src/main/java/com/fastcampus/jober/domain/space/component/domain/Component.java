package com.fastcampus.jober.domain.space.component.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import com.fastcampus.jober.domain.space.component.componentTemp.domain.ComponentTemp;
import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.template.domain.Template;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Component extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_space_wall_id")
    private SpaceWall parentSpaceWall;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "this_space_wall_id")
    private SpaceWall thisSpaceWall;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false)
    private boolean visible;

    @Column(length = 100)
    private String title;

    @Column(length = 3000)
    private String content;

    @Column(nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequence;

    public void update(ComponentTemp componentTemp) {
        if (Objects.equals(this.template, componentTemp.getTemplate())) {
            // 두 객체가 같거나 둘 다 null이면 실행
        } else {
            this.template = componentTemp.getTemplate();
        }

        if (Objects.equals(this.thisSpaceWall, componentTemp.getThisSpaceWall())) {

        } else {
            this.thisSpaceWall = componentTemp.getThisSpaceWall();
        }

        if (this.visible != componentTemp.isVisible()) {
            this.visible = componentTemp.isVisible();
        }
        if (!Objects.equals(this.title, componentTemp.getTitle())) {
            this.title = componentTemp.getTitle();
        }
        if (!Objects.equals(this.content, componentTemp.getContent())) {
            this.content = componentTemp.getContent();
        }
        if (this.sequence != componentTemp.getSequence()) {
            this.sequence = componentTemp.getSequence();
        }
    }

}
