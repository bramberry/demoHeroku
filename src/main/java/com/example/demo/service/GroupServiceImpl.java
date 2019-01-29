package com.example.demo.service;

import com.example.demo.domain.AppConstants;
import com.example.demo.domain.ParametrsDto;
import com.example.demo.domain.User;
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
    public List<User> loadMembers(ParametrsDto dto) throws InterruptedException, ClientException, ApiException {
        List<User> selectedUsers = new ArrayList<>();
        String groupTextName = dto.getGroup();
        dto.setGroup(getById(dto.getGroup()).getId().toString());
        ResponseEntity<String> countRes = restTemplate.exchange(buildGetGroupMembersUrl(dto, 0), HttpMethod.GET, null, String.class);
        int count = ParseUtil.parseResultCount(countRes.getBody());
        for (int i = 0; i < count; i += 1000) {
            ResponseEntity<String> response =
                    restTemplate.exchange(buildGetGroupMembersUrl(dto, i),
                            HttpMethod.GET, null, String.class);
            List<User> inputUsers = ParseUtil.parseString(response.getBody());
            selectedUsers.addAll(userService.filter(inputUsers, groupTextName));
            Thread.sleep(500);
        }
        return selectedUsers;
    }

    @Override
    public GroupFull getById(String group) throws ClientException, ApiException {
        return vk.groups().getById(new ServiceActor(Integer.parseInt(AppConstants.CLIENT_ID.toString())
                , AppConstants.APP_TOKEN.toString())).fields(GroupField.MEMBERS_COUNT).groupId(group).execute().get(0);
    }

    private String buildGetGroupMembersUrl(ParametrsDto dto, int i) {
        return "https://api.vk.com/method/users.search?"
                + "access_token=" + AppConstants.APP_TOKEN
                + "&offset=" + i + "&count=1000"
                + "&group_id=" + dto.getGroup()
                + "&age_from=" + dto.getAgeFrom()
                + "&age_to=" + dto.getAgeTo()
                + "&sex=" + dto.getSex()
                + "&city=" + dto.getCity()
                + "&has_photo=" + dto.getHasPhoto()
                + "&v=5.92&fields=relation,city,sex,bdate";
    }
}
