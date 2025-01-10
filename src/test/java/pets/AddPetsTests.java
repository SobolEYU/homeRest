package pets;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import dto.BaseResponseDTO;
import dto.PetsDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import services.PetsApi;

public class AddPetsTests {

    private static final PetsApi PETS_API = new PetsApi();
    public static ArrayList<Long> addedPets = new ArrayList<>();

    //метод ADD отрабатывает успешно и в ответ присылает присвоенный id питомца
    @Test
    public void addPetsTest() {
        PetsDTO pet = getPet();

        PetsDTO response = PETS_API.addPet(pet).extract().body().as(PetsDTO.class);
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

        PetsDTO responseAdd = PETS_API.addPet(pet).extract().body().as(PetsDTO.class);
        addedPets.add(responseAdd.getId());
        PetsDTO responseGet = PETS_API.getPetById(responseAdd.getId()).extract().body().as(PetsDTO.class);
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

        PetsDTO responseAdd = PETS_API.addPet(pet).extract().body().as(PetsDTO.class);
        PETS_API.deletePetById(responseAdd.getId());
        BaseResponseDTO responseGet = PETS_API.getPetById(responseAdd.getId()).extract().body().as(BaseResponseDTO.class);
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

        PetsDTO responseAdd = PETS_API.addPet(pet).extract().body().as(PetsDTO.class);
        addedPets.add(responseAdd.getId());
        pet.setStatus("не доступен");
        pet.setId(responseAdd.getId());
        PETS_API.updatePet(pet);
        PetsDTO responseGet = PETS_API.getPetById(responseAdd.getId()).extract().body().as(PetsDTO.class);
        assertAll("Получаем то же, что и при создании питомца",
                () -> assertEquals("Baton", responseGet.getName()),
                () -> assertEquals("не доступен", responseGet.getStatus()),
                () -> assertEquals(responseAdd.getId(), responseGet.getId())
        );
    }

    @AfterAll
    public static void deletePets() {
        addedPets.forEach(PETS_API::deletePetById);
    }

    private PetsDTO getPet() {
        return PetsDTO
                .builder()
                .name("Baton")
                .status("доступен")
                .build();
    }
}