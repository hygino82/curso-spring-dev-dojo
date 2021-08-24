package academy.devdojo.springboot2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class AnimeService {
	// private final AnimeRepository animeRepository;

	private static List<Anime> animes;

	static {
		animes = new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Naruto")));
	}

	public List<Anime> listAll() {
		return animes;
	}

	public Anime findById(Long id) {
		return animes.stream().filter(anime -> anime.getId().equals(id)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime id not found"));
	}

	public Anime save(Anime anime) {
		anime.setId(ThreadLocalRandom.current().nextLong(3, 1000000));
		animes.add(anime);
		return anime;
	}

}
