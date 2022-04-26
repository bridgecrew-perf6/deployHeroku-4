package com.company.service;

import com.company.dto.AttachDto;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AttachRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;
    @Value("${server.domen.name}")
    private String urlDowload;
    @Value("${attach.upload.folder}")
    private String attachfolder;
    public AttachEntity saveDb(String key, String filePath, String extension, MultipartFile file) {
        AttachEntity entity = new AttachEntity();
        entity.setExtencion(extension);
        entity.setId(key);
        entity.setPath(filePath);
        entity.setOrgName(file.getOriginalFilename());
        entity.setSize(file.getSize());
        attachRepository.save(entity);
        return entity;
    }
    public AttachDto upload(MultipartFile file) {
        String filePath = getYmDString();
        File folder = new File(attachfolder + filePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

        AttachEntity entity = saveDb(key, filePath, extension, file);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + "/" + key + "." + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toDo(entity, urlDowload + "/attach/dowload/" + key);
    }
    public AttachDto toDo(AttachEntity entity, String url) {
        AttachDto dto = new AttachDto();
        dto.setId(entity.getId());
        dto.setPath(entity.getPath());
        dto.setOrgName(entity.getOrgName());
        dto.setCreateDate(entity.getCreateDate());
        dto.setUrl(url);
        return dto;
    }

    public String upload_simple(MultipartFile file) {
        File folder = new File("uploads");
        if (!folder.exists()) {
            folder.mkdir();
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachfolder + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] open(String fileName) {
        byte[] imageInByte;
        BufferedImage orginalImage;
        try {
            orginalImage = ImageIO.read(new File(attachfolder + fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(orginalImage, "png", baos);

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public byte[] openGeneral(String key) {
        byte[] imageInByte;
        AttachEntity entity = getById(key);
        String fileName = entity.getPath() + "/" + key + "." + entity.getExtencion();
        try {
            Path path = Paths.get(attachfolder + fileName);
            imageInByte = Files.readAllBytes(path);
            return imageInByte;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public ResponseEntity<?> dowload(String key) {
        try {
            AttachEntity entity = getById(key);
            String path = entity.getPath() + "/" + key + "." + entity.getExtencion();
            Path file = Paths.get(attachfolder + path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOrgName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }
    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    public AttachEntity getById(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not found");
        });
    }

    public List<AttachDto> getList(PageRequest of) {
        List<AttachDto> list = new LinkedList<>();
        attachRepository.findAll(of).forEach(entity -> {
            list.add(toDo(entity, urlDowload + "/attach/dowload/" + entity.getId()));
        });
        return list;
    }

    public String delete(String id) {
        AttachEntity entity = attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });

        String path = entity.getPath() + "/" + id + "." + entity.getExtencion();
        File file = new File(attachfolder + path);
        if (file.delete()) {
            attachRepository.deleteById(id);
        }
        return "Success";
    }
    public Boolean updateImage(Integer id, String imageId) {
        ProfileEntity entity = profileService.get(id);
        if (entity.getAttach() != null) {
            delete(imageId);
        }
        profileRepository.updateImage(imageId, id);
        return true;
    }
}
