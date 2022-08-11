package com.heejoo.socketchat.module.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Slf4j
public class RoomController {
    private final ChatRoomRepository repository;

    /* 채팅방 목록 조회 */
    @GetMapping(value = "/rooms")
    public String rooms(Model model) {
        log.info("# All Chat Rooms");
        model.addAttribute("list", repository.finalAllRooms());
        return "/stomp/rooms";
    }

    /* 채팅방 개설 */
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, RedirectAttributes redirectAttributes) {
        log.info("# Create Chat Room, name : " + name);
        redirectAttributes.addFlashAttribute("roomName", repository.createChatRoomDTO(name));
        System.out.println(redirectAttributes.getAttribute("roomName"));
        return "redirect:/chat/rooms";
    }

    /* 채팅방 조회 */
    @GetMapping("/room")
    public String getRoom(@RequestParam(value = "roomId") String roomId, Model model) {
        log.info("# get Chat Room, roomID : " + roomId);
        model.addAttribute("room", repository.findRoomByIdx(roomId));
        model.addAttribute("username", createRandomString());
        System.out.println(model.getAttribute("room"));
        return "/stomp/room";
    }

    private String createRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
