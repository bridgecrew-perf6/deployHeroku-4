package com.company.service;

import com.company.dto.LikeDto;
import com.company.entity.ArticleEntity;
import com.company.entity.LikeEntity;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenexception;
import com.company.exception.ItemNotFoundException;
import com.company.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ArticleService articleService;

    public LikeDto create(Integer id, LikeDto dto) {
        ArticleEntity article = articleService.get(dto.getArticleId());

        LikeEntity entity = new LikeEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(id);
        entity.setStatus(dto.getStatus());
        likeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public Boolean delete(Integer id, Integer pId, ProfileRole role) {
        LikeEntity entity = getById(id);
        if (!entity.getProfileId().equals(pId) || !role.equals(ProfileRole.ADMIN)) {
            throw new AppForbiddenexception("Not Access");
        }
        likeRepository.deleteById(id);
        return true;
    }

    public LikeEntity getById(Integer id) {
        return likeRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Like not found");
        });
    }

    public LikeDto update(Integer id, LikeDto dto, Integer pId) {
        LikeEntity entity = getById(id);
        if (!entity.getProfileId().equals(pId)) {
            throw new AppForbiddenexception("Not Access");
        }
        entity.setStatus(dto.getStatus());
        likeRepository.save(entity);
        return dto;

    }

    public List<LikeDto> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createDate"));
        List<LikeDto> list = new LinkedList<>();
        likeRepository.findAll(pageable).forEach(likeEntity -> {
            list.add(toDto(likeEntity));
        });
        return list;
    }

    public PageImpl<LikeDto> listByArticleId(Integer articleId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createDate"));
        Page<LikeEntity> pageList = likeRepository.findAllByArticleId(articleId, pageable);

        List<LikeDto> dtoList = new LinkedList<>();
        for (LikeEntity entity : pageList.getContent()) {
            dtoList.add(toDto(entity));

        }
        return new PageImpl<>(dtoList, pageable, pageList.getTotalElements());
    }

    public PageImpl<LikeDto> listByprofileId(Integer pId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createDate"));
        Page<LikeEntity> pageList = likeRepository.findAllByProfileId(pId, pageable);

        List<LikeDto> dtoList = new LinkedList<>();
        for (LikeEntity entity : pageList.getContent()) {
            dtoList.add(toDto(entity));

        }
        return new PageImpl<>(dtoList, pageable, pageList.getTotalElements());
    }

    private LikeDto toDto(LikeEntity entity) {
        LikeDto dto = new LikeDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setPId(entity.getProfileId());
        dto.setArticleId(entity.getArticleId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setUpdateDate(entity.getUpdateDate());
        return dto;
    }

    public LikeDto checkLike(Integer articleId,Integer pId){
        Optional<LikeEntity> checkLike = likeRepository.
                findAllByProfileIdAndArticleId(pId, articleId);
        return checkLike.map(this::toDto).orElse(null);
    }


    // 1. LikeEntity
    // 2. Article (region,type,attach,viewCount)
    // getProfileArticle + pagination,
    // list
    // getArticleListBy Region
    // getArticleListBy Category
    // getArticleListBy Type
    // lastAdded4 articles
    // lastAdded 4 articles by region
    // lastAdded 4 articles by category
}
