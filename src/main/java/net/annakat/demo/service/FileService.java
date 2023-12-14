package net.annakat.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.annakat.demo.model.Event;
import net.annakat.demo.model.FileEntity;
import net.annakat.demo.model.Status;
import net.annakat.demo.repository.FileRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

   private final FileRepository fileRepository;
   public final EventService eventService;

  public Mono<Event> makeEvent(String name, Integer id) {
       Event event = new Event();
       event.setEventName(name);
       event.setFileId(id);
       event.setUserId(7);
       event.setStatus(Status.ACTIVE);
       return eventService.createEvent(event);
   }


    public Flux<FileEntity> getAll() {
       return fileRepository.findAll();
    }

    public Mono<FileEntity> getFileById(Integer id) {
        return fileRepository.findById(id);
    }

    public Mono<FileEntity> saveFileEntity(FileEntity file) {
        file.setStatus(Status.ACTIVE);
       return fileRepository.save(file)
                .flatMap(fileEntity -> makeEvent("create", fileEntity.getId()).thenReturn(fileEntity));
    }

    public Mono<FileEntity> updateFileEntity(FileEntity file) {
       return fileRepository.findById(file.getId()).flatMap(fileEntity -> {
            file.setStatus(fileEntity.getStatus());
            file.setLocation(fileEntity.getLocation());
            file.setStatus(Status.ACTIVE);
            return fileRepository.save(file);
        }).flatMap(fileEntity -> makeEvent("update", fileEntity.getId()).thenReturn(fileEntity));
    }

    public Mono<Boolean> deleteFileEntity(Integer id) {
      return fileRepository.deleteFile(id, String.valueOf(Status.DELETE))
              .flatMap(result -> makeEvent("deleted", id)
                      .thenReturn(result));
    }
}
