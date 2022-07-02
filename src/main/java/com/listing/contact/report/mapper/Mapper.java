package com.listing.contact.report.mapper;

import com.listing.contact.report.dto.ContactDTO;
import com.listing.contact.report.dto.MakeDistributionDTO;
import com.listing.contact.report.dto.TopContactedListingDTO;
import com.listing.contact.report.dto.TopThirtyPercentAvgDTO;
import com.listing.contact.report.enums.APIErrorCode;
import com.listing.contact.report.exception.WrongDoubleFormatException;
import com.listing.contact.report.model.Contact;
import com.listing.contact.report.util.DoubleRounderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Mapper {
    private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);

    public static List<ContactDTO> toContactDTOS(List<Contact> contacts) {
        List<ContactDTO> contactDTOS = contacts.stream()
                                    .map(contact -> new ContactDTO(contact.getListingId(),
                                            unixToLocalDateTime(contact.getContactDate())))
                .collect(Collectors.toList());

        return contactDTOS;
    }

    private static LocalDateTime unixToLocalDateTime(Long unix) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(unix),
                        TimeZone.getDefault().toZoneId());

        return localDateTime;
    }

    public static List<TopContactedListingDTO> toTopContactedListingDTOS(List<Object[]> objects) {
        List<TopContactedListingDTO> topContactedListingDTOS = objects.stream()
                .map(object -> toTopContactedListingDTO(object)).collect(Collectors.toList());

        return topContactedListingDTOS;
    }

    private static TopContactedListingDTO toTopContactedListingDTO(Object[] obj) {
        TopContactedListingDTO topContactedListingDTO = TopContactedListingDTO.builder()
                .listingId(Long.parseLong(obj[0].toString()))
                .make(obj[1].toString())
                .price(obj[2].toString() + " \u20ac")
                .mileage(obj[3].toString() + " KM")
                .totalCount(Integer.parseInt(obj[4].toString()))
                .build();

        return topContactedListingDTO;
    }

    public static List<MakeDistributionDTO> toMakeDistributionDTOS(List<Object[]> objects) {
        List<MakeDistributionDTO> makeDistributionDTOS = objects.stream()
                .map(object -> toMakeDistributionDTO(object)).collect(Collectors.toList());

        return makeDistributionDTOS;
    }

    private static MakeDistributionDTO toMakeDistributionDTO(Object[] obj) {
        MakeDistributionDTO makeDistributionDTO = MakeDistributionDTO.builder()
                .make(obj[0].toString())
                .distribution(obj[1].toString() + "%")
                .build();

        return makeDistributionDTO;
    }

    public static TopThirtyPercentAvgDTO toTopThirtyPercentAvgDTO(Double topThirtyPercentAvg) {
        if (topThirtyPercentAvg == null) {
            LOG.info(String.format("Wrong Double value provided %s", topThirtyPercentAvg));
            throw new WrongDoubleFormatException(APIErrorCode.WRONG_DOUBLE_FORMAT);
        }

        String topThirtyPercentAvgEuro = DoubleRounderUtil.roundDoubleTwoDecimal(topThirtyPercentAvg).toString();

        TopThirtyPercentAvgDTO topThirtyPercentAvgDTO = TopThirtyPercentAvgDTO.builder()
                .average(topThirtyPercentAvgEuro + " \u20ac").build();

        return topThirtyPercentAvgDTO;
    }
}
