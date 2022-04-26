package com.company.controller;

import com.company.dto.AttachDto;
import com.company.enums.ProfileRole;
import com.company.service.AttachService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping(value = "/open/{fileName}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String file){
        return attachService.open(file);
    }

    @GetMapping(value = "/open1/{fileName}",produces = MediaType.ALL_VALUE)
    public byte[] openGeneral(@PathVariable("fileName") String file){
        return attachService.openGeneral(file);
    }

    @GetMapping(value = "/dowload/{fileName}")
    public ResponseEntity<?> dowload(@PathVariable("fileName") String fileName){
        return attachService.dowload(fileName);

    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(@RequestParam(value = "page",defaultValue = "0") int page,
                                     @RequestParam(value = "size",defaultValue = "10") int size,
                                     HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.getList(PageRequest.of(page,size)));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable String id,HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(id));
    }


    @PostMapping("")
    public ResponseEntity<?> updateImage(AttachDto image,HttpServletRequest request){
        Integer id = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(attachService.updateImage(id,image.getId()));

    }


    // 1.upload - separate
    // 2.Attach RUD with pagination
    // 3. Attach delete (from entity+system)
    // 4.Email History - RD + list with pagination
    // 5. 5.8-finish


}
