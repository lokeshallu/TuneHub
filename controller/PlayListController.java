package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.services.PlaylistService;
import com.tunehub.services.SongService;

@Controller
public class PlayListController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model){
		List<Song> songList= songService .fetchAllSongs();
		model.addAttribute("songs",songList);
		return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);
		List<Song> songList= playlist.getSongs();
		for (Song s: songList) {
			s.getPlaylist().add(playlist);
			songService.updateSong(s);
			//update the song object in db..
		}
		return "adminHome";
	}
	@GetMapping("/viewPlaylists")
	public String viewSongs(Model model) {
		List<Playlist> playlists = playlistService.fetchAllPlaylists();
		model.addAttribute("playlists",playlists);
		return "displayPlaylist";

	}
}
