package com.company.service;

import com.company.dto.ArticleDto;
import com.company.dto.CategoryDto;
import com.company.entity.ArticleEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ArticleRepository;
import com.company.validation.ArticleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;
    @Autowired
    private ProfileService service;

    public ArticleDto create(ArticleDto dto,Integer pId) {
        ArticleValidation.isValid(dto);
        ArticleEntity article = new ArticleEntity();
        article.setContent(dto.getContent());
        article.setDescription(dto.getDescription());
        article.setTitle(dto.getTitle());
        article.setProfileId(pId);
        repository.save(article);
        dto.setId(article.getId());
        return dto;
    }

    public ArticleDto update(Integer id, ArticleDto dto, Integer pId) {
        ArticleValidation.isValid(dto);
        Optional<ArticleEntity> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("bunday article yoq");
        }
        ArticleEntity article = byId.get();
        article.setContent(dto.getContent());
        article.setDescription(dto.getDescription());
        article.setTitle(dto.getTitle());
        repository.save(article);

        return toDo(article);
    }

    public boolean delete(Integer id){
        Optional<ArticleEntity> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("bunday article yoq");
        }
        repository.deleteById(id);
        return true;
    }

    public List<ArticleDto> getList(){
        List<ArticleDto> list = new LinkedList<>();
        repository.findAll().forEach(entity->{
            list.add(toDo(entity));
        });
        return list;
    }

    public List<ArticleDto> list(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<ArticleDto> list = new LinkedList<>();
        repository.findAll(pageable).forEach(entity->{
            list.add(toDo(entity));
        });
        return list;
    }

    public ArticleEntity get(Integer aticleId){
        return repository.findById(aticleId).orElseThrow(()->{
            throw new ItemNotFoundException("Article not found");
        });
    }

    private ArticleDto toDo(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setCreateDate(entity.getCreateDate());
        dto.setViewCount(entity.getViewCount());
        dto.setRegionId(entity.getRegionId());
        dto.setTypeId(entity.getTypeId());
        return dto;

    }

    public ArticleDto getById(Integer id) {
        ArticleEntity article = get(id);
        repository.updateViewCount(article.getViewCount()+1,id);
        return  toDo(article);
    }

    // getProfileArticle + pagination,
    public List<ArticleDto> getProfileArticle(Integer pId,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<ArticleDto> list = new LinkedList<>();
        repository.findAllByProfileId(pId,pageable).getContent().forEach(entity -> {
           list.add(toDo(entity));
        });
        return list;
    }
    // getArticleListBy Region
    public List<ArticleDto> getArticleByRegion(Integer id,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<ArticleDto> list = new LinkedList<>();
        repository.findAllByRegionId(id,pageable).getContent().forEach(entity -> {
            list.add(toDo(entity));
        });
        return list;
    }
    // getArticleListBy Category
    public List<ArticleDto> getArticleByCategory(Integer id,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<ArticleDto> list = new LinkedList<>();
        repository.findAllByCategoryEntityId(id,pageable).getContent().forEach(entity -> {
            list.add(toDo(entity));
        });
        return list;
    }
    // getArticleListBy Type
    public List<ArticleDto> getArticleByType(Integer id,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<ArticleDto> list = new LinkedList<>();
        repository.findAllByArticleTypeId(id,pageable).getContent().forEach(entity -> {
            list.add(toDo(entity));
        });
        return list;
    }
    // lastAdded4 articles
    public List<ArticleDto> last4(){
        List<ArticleDto> list = new LinkedList<>();
        repository.findTop4By(Sort.by(Sort.Direction.DESC,"createDate")).
                forEach(entity -> {list.add(toDo(entity));
        });
        return list;
    }
    // lastAdded 4 articles by region
    public List<ArticleDto> last4ByRegion(Integer regionId){
        List<ArticleDto> list = new LinkedList<>();
        repository.findTop4ByRegionId(regionId,Sort.by(Sort.Direction.DESC,"createDate")).
                forEach(entity -> {list.add(toDo(entity));
                });
        return list;
    }
    // lastAdded 4 articles by category
    public List<ArticleDto> last4ByCategory(Integer categoryId){
        List<ArticleDto> list = new LinkedList<>();
        repository.findTop4ByCategoryEntityId(categoryId,Sort.by(Sort.Direction.DESC,"createDate")).
                forEach(entity -> {list.add(toDo(entity));
                });
        return list;
    }
}
