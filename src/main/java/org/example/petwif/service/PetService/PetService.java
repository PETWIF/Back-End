package org.example.petwif.service.PetService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.entity.Pet;
import org.example.petwif.domain.enums.Gender;
import org.example.petwif.domain.enums.PetGender;
import org.example.petwif.domain.enums.Telecom;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.repository.PetRepository;
import org.example.petwif.web.dto.PetDto.PetRequestDto;
import org.example.petwif.web.dto.PetDto.PetResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    public List<PetResponseDto> addPet(Long id, List<PetRequestDto> dto){
        Member member = memberRepository.findByMemberId(id);
        List<Pet> petList = new ArrayList<>();
        for (PetRequestDto petRequestDto : dto){
            Pet pet = new Pet();
            pet.setPetAge(petRequestDto.getAge());
            pet.setPetGender(petRequestDto.getGender());
            pet.setPetName(petRequestDto.getPetName());
            pet.setPetKind(petRequestDto.getPetKind());
            pet.setMember(member);
            petRepository.save(pet);
            petList.add(pet);
        }
        return Pets(petList);

    }

    public PetResponseDto editPet(Long mId, Long petId, PetRequestDto dto){
        Member member = memberRepository.findByMemberId(mId);
        Pet pet = petRepository.findById(petId).orElseThrow(()
                -> new IllegalArgumentException("Pet not found"));

        if ((dto.getAge()) != null) pet.setPetAge(dto.getAge());
        if ((dto.getGender()) != null) pet.setPetGender(dto.getGender());
        if ((dto.getPetName()) != null) pet.setPetName(dto.getPetName());
        if (dto.getPetKind() != null) pet.setPetKind(dto.getPetKind());
        pet.setMember(member);
        petRepository.save(pet);
        return mapPetToResponse(pet);
    }

    private PetResponseDto mapPetToResponse(Pet pet) {
        return PetResponseDto.builder()
                .petId(pet.getId())
                .petName(pet.getPetName())
                .petKind(pet.getPetKind())
                .age(pet.getPetAge())
                .gender(pet.getPetGender())
                .build();
    }

    public List<PetResponseDto> getAllPets(Long id){
        List<Pet> pets = petRepository.findPetsByMember(memberRepository.findByMemberId(id));
        return pets.stream()
                .map(this::mapPetToResponse)
                .collect(Collectors.toList());
    }

    public List<PetResponseDto> Pets(List<Pet> pets){
        return pets.stream()
                .map(this::mapPetToResponse)
                .collect(Collectors.toList());
    }

    public void deletePet(Long id){
        Pet pet = petRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Pet not found"));
        petRepository.delete(pet);
    }
}
