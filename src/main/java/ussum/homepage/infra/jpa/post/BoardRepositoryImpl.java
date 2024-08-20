package ussum.homepage.infra.jpa.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.BoardRepository;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.repository.BoardJpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
    private final BoardJpaRepository boardJpaRepository;
    private final BoardMapper boardMapper;
    @Override
    public Optional<Board> findById(Long id){
        return boardJpaRepository.findById(id).map(boardMapper::toDomain);
    }
    @Override
    public Optional<Board> findByBoardCode(String boardCode){
        return boardJpaRepository.findByBoardCode(BoardCode.getEnumBoardCodeFromStringBoardCode(boardCode)).map(boardMapper::toDomain);
    }
    @Override
    public Page<Board> findAll(Pageable pageable){
        return boardJpaRepository.findAll(pageable).map(boardMapper::toDomain);
    }
    @Override
    public Board save(Board board){
        return boardMapper.toDomain(boardJpaRepository.save(boardMapper.toEntity(board)));
    }
    @Override
    public void delete(Board board){
        boardJpaRepository.delete(boardMapper.toEntity(board));
    }
}
