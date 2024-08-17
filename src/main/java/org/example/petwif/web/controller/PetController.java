package org.example.petwif.web.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.entity.Pet;
import org.example.petwif.service.MemberService.MemberService;
import org.example.petwif.service.PetService.PetService;
import org.example.petwif.web.dto.PetDto.PetRequestDto;
import org.example.petwif.web.dto.PetDto.PetResponseDto;
import org.springframework.web.bind.annotation.*;
import static org.example.petwif.apiPayload.code.status.ErrorStatus._BAD_REQUEST;
import static org.example.petwif.apiPayload.code.status.SuccessStatus.OK;
import java.util.List;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final MemberService memberService;

    @GetMapping("/view")
    public ApiResponse<List<PetResponseDto>> viewAllMyPets(@RequestHeader("Authorization")
                                          String authorizationHeader) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        Long id = member.getId();
        try {
            return ApiResponse.onSuccess(petService.getAllPets(id));
        } catch (Exception e) {
            throw new GeneralException(_BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ApiResponse<List<PetResponseDto>> newPet(@RequestHeader("Authorization")
                                                    String authorizationHeader,
                                                    @RequestBody List<PetRequestDto> dto) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        Long id = member.getId();
        try {
            return ApiResponse.onSuccess(petService.addPet(id, dto));
        } catch (Exception e) {
            throw new GeneralException(_BAD_REQUEST);
        }
    }

    @PatchMapping("/edit")
    public ApiResponse<PetResponseDto> editPet(@RequestHeader("Authorization")
                                       String authorizationHeader, @RequestBody PetRequestDto dto,
                                       @RequestParam("petId") Long petId){
        Member member = memberService.getMemberByToken(authorizationHeader);
        Long id = member.getId();

        try{
            return ApiResponse.onSuccess(petService.editPet(id, petId, dto));
        } catch (Exception e){
            throw new GeneralException(_BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> deletePet(@RequestParam("id") Long id){
        try{
            petService.deletePet(id);
            return ApiResponse.onSuccess("id가 "+id+"인 Pet 삭제 완료");
        } catch (IllegalArgumentException e){
            return ApiResponse.onFailure("400", "error", e.getMessage());
        }
    }

}
