package net.annakat.demo.rest;

import lombok.RequiredArgsConstructor;
import net.annakat.demo.dto.FileDto;
import net.annakat.demo.mapper.FileMapper;
import net.annakat.demo.model.FileEntity;
import net.annakat.demo.service.FileService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileRestControllerV1 {

    private final FileService fileService;
    private final FileMapper fileMapper;

    @PostMapping()
    public Mono<FileDto> createFile (@RequestBody FileDto dto) {
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.saveFileEntity(fileEntity).map(fileMapper::map);
    }


    @GetMapping("/{id}")
    public Mono<FileDto> getFile(@PathVariable("id") Integer id) {
      return fileService.getFileById(id).map(fileMapper::map);
    }

    @PostMapping("/update")
    public Mono<FileDto> updateFile(@RequestBody FileDto dto) {
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.updateFileEntity(fileEntity).map(fileMapper::map);
    }

    @PostMapping("/{id}")
    public Mono<Boolean> deleteFile(@PathVariable("id") Integer id) {
        return fileService.deleteFileEntity(id);
    }

    @GetMapping()
    public Flux<FileDto> getAllFile() {
        return fileService.getAll().map(fileMapper::map);
    }

}
