package academy.devdojo.springboot2.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.request.AnimePostRequestBody;
import academy.devdojo.springboot2.request.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
	// http://localhost:8080/anime/list
	private final DateUtil dateUtil;
	private final AnimeService animeService;

	@GetMapping
	public ResponseEntity<List<Anime>> list() {
		log.info(dateUtil.formatLocalDateTimeToDatabasestyle(LocalDateTime.now()));
		return ResponseEntity.ok(animeService.listAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable Long id) {
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}
	
	@GetMapping(path = "/find")//requestparam evita ambiguidade
	public ResponseEntity<List<Anime>> findByName(@RequestParam(required = false) String name) {
		return ResponseEntity.ok(animeService.findByName(name));
	}

	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
		return new ResponseEntity<>(animeService.save(animePostRequestBody),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		animeService.delete(id);
		return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
		animeService.replace(animePutRequestBody);
		return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}
