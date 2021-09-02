package academy.devdojo.springboot2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.request.AnimePostRequestBody;
import academy.devdojo.springboot2.request.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimeService {
	private final AnimeRepository animeRepository;

	public List<Anime> listAll() {
		return animeRepository.findAll();
	}

	public List<Anime> findByName(String name) {
		return animeRepository.findByName(name);
	}

	public Anime findByIdOrThrowBadRequestException(Long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime id not found"));
	}

	public Anime save(AnimePostRequestBody animeAnimePostRequestBody) {

		return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animeAnimePostRequestBody));
	}

	public void delete(Long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(AnimePutRequestBody animePutRequestBody) {
		Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
		Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
		anime.setId(savedAnime.getId());
		animeRepository.save(anime);
	}

}
