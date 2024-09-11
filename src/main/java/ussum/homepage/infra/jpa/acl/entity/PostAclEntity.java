package ussum.homepage.infra.jpa.acl.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.PostEntity;

@Entity
@Table(name = "post_acl")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAclEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TargetGroup targetGroup;

    @Enumerated(EnumType.STRING)
    private Target target;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Action action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    private PostAclEntity(Long id, TargetGroup targetGroup, Target target, Type type, Action action, BoardEntity boardEntity) {
        this.id = id;
        this.targetGroup = targetGroup;
        this.target = target;
        this.type = type;
        this.action = action;
        this.boardEntity = boardEntity;
    }

    public static PostAclEntity of(Long id, TargetGroup targetGroup, Target target, Type type, Action action, BoardEntity boardEntity){
        return new PostAclEntity(id, targetGroup, target, type, action, boardEntity);
    }
}
