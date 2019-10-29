package tech.jdevmin.web.jpa.dto;

import java.util.ArrayList;
import java.util.List;

public class GitLinks {

    public static final List<String> links = new ArrayList<>();
    private static final String GIT_PREFIX = "https://github.com/jayJavaGGDev/";
    private static final String ALBUMS_SERVICE = GIT_PREFIX + "Albums-Service";
    private static final String ACCOUNT_MANAGEMENT = GIT_PREFIX + "Account-Management";
    private static final String ZUUL_API_GATEWAY = GIT_PREFIX + "Zuul-Api-Gateway";
    private static final String DISCOVERY_SERVICE = GIT_PREFIX + "Discovery-Service";
    private static final String USERS_WS = GIT_PREFIX + "Users-WS";
    private static final String CONFIG_SERVER = GIT_PREFIX + "Config-Server";
    private static final String SPRING_TODO = GIT_PREFIX + "SpringTodo";
    private static final String MOBILE_APP_WS = GIT_PREFIX + "mobile-app-ws";
    private static final String PHOTO_APP_CONFIGURATION = GIT_PREFIX + "PhotoAppConfiguration";
    private static final String OBSTACLE_AVOID_TUT = GIT_PREFIX + "ObstacleAvoidGameTutorialProject";
    private static final String LIBGDX_TOOLKIT_PLAYGROUND = GIT_PREFIX + "MyLibGDXToolKitPlayground";
    private static final String BATTLE_FOR_THALANAAR = GIT_PREFIX + "BattleForThalanaar";
    public GitLinks() {
        links.add(ALBUMS_SERVICE);
        links.add(ACCOUNT_MANAGEMENT);
        links.add(ZUUL_API_GATEWAY);
        links.add(DISCOVERY_SERVICE);
        links.add(USERS_WS);
        links.add(CONFIG_SERVER);
        links.add(SPRING_TODO);
        links.add(MOBILE_APP_WS);
        links.add(PHOTO_APP_CONFIGURATION);
        links.add(OBSTACLE_AVOID_TUT);
        links.add(LIBGDX_TOOLKIT_PLAYGROUND);
        links.add(BATTLE_FOR_THALANAAR);
    }


}
