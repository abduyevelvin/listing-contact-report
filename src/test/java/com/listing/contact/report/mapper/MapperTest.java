package com.listing.contact.report.mapper;

import com.listing.contact.report.dto.ContactDTO;
import com.listing.contact.report.dto.MakeDistributionDTO;
import com.listing.contact.report.dto.TopContactedListingDTO;
import com.listing.contact.report.dto.TopThirtyPercentAvgDTO;
import com.listing.contact.report.model.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MapperTest {
    @Test
    void toContactDTOSTest() {
        List<Contact> contacts = new ArrayList<>();
        Contact contact1 = Contact.builder().listingId(1244L).contactDate(1592498493000L).build();
        Contact contact2 = Contact.builder().listingId(1085L).contactDate(1582474057000L).build();
        contacts.add(contact1);
        contacts.add(contact2);

        List<ContactDTO> contactDTOS = Mapper.toContactDTOS(contacts);

        Assertions.assertEquals(contacts.size(), contactDTOS.size());
        Assertions.assertEquals(contact1.getListingId(), contactDTOS.get(0).getListingId());
        Assertions.assertEquals("2020-02-23T17:07:37", contactDTOS.get(1).getContactDate().toString());
    }

    @Test
    void toTopContactedListingDTOSTest() {
        List<Object[]> objects = new ArrayList<>();
        Object[] firstObjectArray = new Object[]{1L,"Audi", 2234.54, 20000L, 15};
        Object[] secondObjectArray = new Object[]{2L,"BMW", 3234.54, 10000L, 18};
        objects.add(firstObjectArray);
        objects.add(secondObjectArray);

        List<TopContactedListingDTO> topContactedListingDTOS = Mapper.toTopContactedListingDTOS(objects);

        Assertions.assertEquals(objects.size(), topContactedListingDTOS.size());
        Assertions.assertEquals(firstObjectArray[0], topContactedListingDTOS.get(0).getListingId());
        Assertions.assertEquals(firstObjectArray[1], topContactedListingDTOS.get(0).getMake());
        Assertions.assertEquals(secondObjectArray[2] + " \u20ac", topContactedListingDTOS.get(1).getPrice());
        Assertions.assertEquals(secondObjectArray[3] + " KM", topContactedListingDTOS.get(1).getMileage());
        Assertions.assertEquals(secondObjectArray[4], topContactedListingDTOS.get(1).getTotalCount());
    }

    @Test
    void toMakeDistributionDTOSTest() {
        List<Object[]> objects = new ArrayList<>();
        Object[] firstObjectArray = new Object[]{"Audi", 55};
        Object[] secondObjectArray = new Object[]{"BMW", 43};
        objects.add(firstObjectArray);
        objects.add(secondObjectArray);

        List<MakeDistributionDTO> makeDistributionDTOS = Mapper.toMakeDistributionDTOS(objects);

        Assertions.assertEquals(objects.size(), makeDistributionDTOS.size());
        Assertions.assertEquals(firstObjectArray[0], makeDistributionDTOS.get(0).getMake());
        Assertions.assertEquals(secondObjectArray[1] + "%", makeDistributionDTOS.get(1).getDistribution());
    }

    @Test
    void toTopThirtyPercentAvgDTOTest() {
        Double topThirtyPercentAvg = 12345.67;
        String topThirtyPercentAvgInEuro = topThirtyPercentAvg + " \u20ac";

        TopThirtyPercentAvgDTO topThirtyPercentAvgDTO = Mapper.toTopThirtyPercentAvgDTO(topThirtyPercentAvg);

        Assertions.assertEquals(topThirtyPercentAvgInEuro, topThirtyPercentAvgDTO.getAverage());
    }
}
