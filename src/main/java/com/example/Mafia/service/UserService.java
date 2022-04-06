package com.example.Mafia.service;


import com.example.Mafia.configuration.ServicesConnection;
import com.example.Mafia.controller.UserController;
import com.example.Mafia.model.User;
import com.example.Mafia.repository.UserRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class UserService  {

private final UserRepository userRepository;
private final RestTemplate restTemplate;
private final ServicesConnection connection;

    @Autowired
    public UserService(UserRepository userRepository, HttpComponentsClientHttpRequestFactory factory, ServicesConnection connection) {
        this.userRepository = userRepository;
        this.restTemplate = new RestTemplate(factory);
        this.connection = connection;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${my.app.secret}")
    private String jwtSecret;

    @Value("${user.app.secret}")
    private String jwtUserSecret;

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        List<User> list = findAll();
        Long number;
        try {
            number = list.stream().max((o1, o2) -> (int) (o1.getUserId() - o2.getUserId())).get().getUserId();
        } catch (NoSuchElementException e) {
            number = Long.valueOf(99);
        }
        user.setUserId(++number);
        return userRepository.save(user);
    }

    public User updateById(Long userId, User user) {

        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();

            if(user.getName() !=null)
            newUser.setName(user.getName());
            if(user.getBandId() !=null)
            newUser.setBandId(user.getBandId());
            if(user.getTaskId() !=null)
            newUser.setTaskId(user.getTaskId());

        return userRepository.save(newUser);

    }

    public ResponseEntity<Void> deleteById(Long userId){

        try {
            userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    public User updateBandId(Long userId, String bandName,  HttpServletRequest request) {

        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        ResponseEntity<String> response = restTemplate.exchange(connection.getUrlBands() + bandName, HttpMethod.GET, new HttpEntity<>(createHeaders(request.getHeader("Authorization"))), String.class);

        try {
            String jsonStr = new String((response.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(jsonStr);
            Long bandId = Long.valueOf(String.valueOf(jsonObject.get("id")));
            newUser.setBandId(bandId);
            return userRepository.save(newUser);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public User updateTaskId(Long userId, String taskName) {

        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        ResponseEntity<String> response = restTemplate.exchange(connection.getUrlTasks() + taskName, HttpMethod.GET, null, String.class);

        try {
            String jsonStr = new String((response.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(jsonStr);
            Long taskId =  Long.valueOf(String.valueOf(jsonObject.get("id")));
            newUser.setTaskId(taskId);
            return userRepository.save(newUser);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isTokenValidUser(HttpServletRequest request){
        try {
            String headerAuth = request.getHeader("Authorization");
            logger.info("Checking for the presence of a token");
            if (headerAuth!=null && headerAuth.startsWith("Bearer ")) {
                String s = Jwts.parser().setSigningKey(jwtUserSecret).parseClaimsJws(headerAuth.substring(7)).getBody().getSubject();
                logger.info("jwtToken received");
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }

    public void isTokenValidBoss(HttpServletRequest request){

            String headerAuth = request.getHeader("Authorization");
            logger.info("Checking for the presence of a token");
            if (headerAuth!=null && headerAuth.startsWith("Bearer ")) {
                String[] s = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(headerAuth.substring(7)).getBody().getSubject().split(" ");
                logger.info("jwtToken received");
                if (s[2].contains("ROLE_BOSS")) {
                    return;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
    }

    public void isTokenValidBossAndUser(Long userId, HttpServletRequest request) {
            String headerAuth = request.getHeader("Authorization");
            logger.info("Checking for the presence of a token");
            if (headerAuth!=null && headerAuth.startsWith("Bearer ")) {
                String[] s = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(headerAuth.substring(7)).getBody().getSubject().split(" ");
                logger.info("jwtToken received");
                if (s[2].contains("ROLE_BOSS")) {
                    return;
                }else if(s[2].contains("ROLE_USER") && s[0].contains(String.valueOf(userId))){
                    return;
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
    }

    private HttpHeaders createHeaders(String jwt) {
        return new HttpHeaders() {{
            set("Authorization", jwt);
        }};
    }
}
