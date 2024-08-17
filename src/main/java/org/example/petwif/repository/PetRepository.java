package org.example.petwif.repository;

import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {


    Optional<Pet> findById(Long id);


    List<Pet> findPetsByMember(Member member);

}
