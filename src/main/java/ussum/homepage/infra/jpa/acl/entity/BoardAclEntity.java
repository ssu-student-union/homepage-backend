//package ussum.homepage.infra.jpa.acl.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import ussum.homepage.infra.jpa.post.entity.BoardEntity;
//
//@Entity
//@Table(name = "board_acl")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class BoardAclEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Enumerated(EnumType.STRING)
//    private Target target;
//    @Enumerated(EnumType.STRING)
//    private Type type;
//    @Enumerated(EnumType.STRING)
//    private Action action;
////    @Enumerated(EnumType.STRING)
////    private OrderType orderType;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
//    private BoardEntity boardEntity;
//    private BoardAclEntity(Long id, Target target, Type type, Action action, /*OrderType orderType,*/ BoardEntity boardEntity){
//        this.id = id;
//        this.target = target;
//        this.type = type;
//        this.action = action;
////        this.orderType = orderType;
//        this.boardEntity = boardEntity;
//    }
//    public static BoardAclEntity of(Long id, Target target, Type type, Action action, /*OrderType orderType,*/ BoardEntity boardEntity){
//        return new BoardAclEntity(id, target, type, action, boardEntity);
//    }
//}
//
