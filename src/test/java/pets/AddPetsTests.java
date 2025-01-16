package pets;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import com.google.inject.Inject;
import dto.BaseResponseDTO;
import dto.PetsDTO;
import extensions.BaseExtensions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.PetsApi;


@ExtendWith(BaseExtensions.class)
public class AddPetsTests {

    @Inject
    private PetsApi petsApi;
    
    private static ArrayList<Long> addedPets = new ArrayList<>();

    //метод ADD отрабатывает успешно и в ответ присылает присвоенный id питомца
    @Test
    public void addPetsTest() {
        PetsDTO pet = getPet();

        PetsDTO response = petsApi.addPet(pet).extract().body().as(PetsDTO.class);
        addedPets.add(response.getId());
        assertAll("Получаем данные переданные при создании + id",
                () -> assertEquals("Baton", response.getName()),
                () -> assertEquals("доступен", response.getStatus()),
                () -> assertNotNull(response.getId())
        );
    }

    //при запросе метода GET мы получим такие же данные, какие отдавали при методе ADD
    @Test
    public void getByIdTest() {
        PetsDTO pet = getPet();

        PetsDTO responseAdd = petsApi.addPet(pet).extract().body().as(PetsDTO.class);
        addedPets.add(responseAdd.getId());
        PetsDTO responseGet = petsApi.getPetById(responseAdd.getId()).extract().body().as(PetsDTO.class);
        assertAll("Получаем то же, что и при создании питомца",
                () -> assertEquals("Baton", responseGet.getName()),
                () -> assertEquals("доступен", responseGet.getStatus()),
                () -> assertEquals(responseAdd.getId(), responseGet.getId())
        );
    }

    //после удаления нельзя получить информацию о питомце по id
    @Test
    public void getByIdAfterDeletePetTest() {
        PetsDTO pet = getPet();

        PetsDTO responseAdd = petsApi.addPet(pet).extract().body().as(PetsDTO.class);
        petsApi.deletePetById(responseAdd.getId());
        BaseResponseDTO responseGet = petsApi.getPetById(responseAdd.getId()).extract().body().as(BaseResponseDTO.class);
        assertAll("Получаем то же, что и при создании питомца",
                () -> assertEquals("Pet not found", responseGet.getMessage()),
                () -> assertEquals("error", responseGet.getType()),
                () -> assertEquals(1, responseGet.getCode())
        );
    }

    //update статуса проходит успешно
    @Test
    public void updatePetStatusTest() {
        PetsDTO pet = getPet();

        PetsDTO responseAdd = petsApi.addPet(pet).extract().body().as(PetsDTO.class);
        addedPets.add(responseAdd.getId());
        pet.setStatus("не доступен");
        pet.setId(responseAdd.getId());
        petsApi.updatePet(pet);
        PetsDTO responseGet = petsApi.getPetById(responseAdd.getId()).extract().body().as(PetsDTO.class);
        assertAll("Получаем то же, что и при создании питомца",
                () -> assertEquals("Baton", responseGet.getName()),
                () -> assertEquals("не доступен", responseGet.getStatus()),
                () -> assertEquals(responseAdd.getId(), responseGet.getId())
        );
    }

    @AfterEach
    public void deletePets() {
        addedPets.forEach(petsApi::deletePetById);
    }

    private PetsDTO getPet() {
        return PetsDTO
                .builder()
                .name("Baton")
                .status("доступен")
                .build();
    }
}