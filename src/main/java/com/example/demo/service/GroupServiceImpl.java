package com.example.demo.service;

import com.example.demo.domain.ParametersDto;
import com.example.demo.domain.VkUser;
import com.example.demo.domain.enumirations.AppConstants;
import com.example.demo.exception.GroupNotFoundException;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.queries.groups.GroupField;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final VkApiClient vk = new VkApiClient(new HttpTransportClient());

    @Override
    public List<VkUser> loadMembers(ParametersDto dto, String token) throws InterruptedException, ClientException, ApiException {
        List<VkUser> selectedVkUsers = new ArrayList<>();
        String groupTextName = dto.getGroup();
        dto.setGroup(getById(dto.getGroup()).getId().toString());
        ResponseEntity<String> countRes = restTemplate.exchange(buildGetGroupMembersUrl(dto, 0, token), HttpMethod.GET, null, String.class);
        int count = ParseUtil.parseResultCount(countRes.getBody());
        for (int i = 0; i < count; i += 1000) {
            ResponseEntity<String> response =
                    restTemplate.exchange(buildGetGroupMembersUrl(dto, i, token),
                            HttpMethod.GET, null, String.class);
            List<VkUser> inputVkUsers = ParseUtil.parseString(response.getBody());
            selectedVkUsers.addAll(userService.filter(inputVkUsers, groupTextName));
            Thread.sleep(500);
        }
        return selectedVkUsers;
    }

    @Override
    public GroupFull getById(String group) throws ClientException, ApiException {
        return vk.groups()
                .getById(
                        new ServiceActor(Integer.parseInt(AppConstants.CLIENT_ID.getValue())
                                , AppConstants.APP_TOKEN.getValue())
                )
                .fields(GroupField.MEMBERS_COUNT)
                .groupId(group)
                .execute()
                .stream()
                .findFirst()
                .orElseThrow(() -> new GroupNotFoundException("Group " + group + " not found"));
    }

    private String buildGetGroupMembersUrl(ParametersDto dto, int offset, String token) {
        return "https://api.vk.com/method/users.search?"
                + "access_token=" + token
                + "&offset=" + offset + "&count=1000"
                + "&group_id=" + dto.getGroup()
                + "&age_from=" + dto.getAgeFrom()
                + "&age_to=" + dto.getAgeTo()
                + "&sex=" + dto.getSex()
                + "&city=" + dto.getCity()
                + "&has_photo=" + dto.getHasPhoto()
                + "&v=5.103&fields=relation,city,sex,bdate,photo_max_orig";
    }
}
