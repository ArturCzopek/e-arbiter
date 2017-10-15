const devData = {
  serverUrl: 'http://192.168.44.1:8080',
  authUrl:  'http://192.168.44.1:8090'
}

export const environment = {
  production: true,
  authToken: 'oauth-token',
  githubUrl: 'http://github.com',
  server: {
    auth: {
      url: devData.serverUrl,
      logoutUrl: `${devData.serverUrl}/auth/logout`,
      logoutGatewayUrl: `${devData.serverUrl}/auth/api/logout`,
      userUrl: `${devData.serverUrl}/auth/api/user`,
      meUrl:  `${devData.serverUrl}/auth/api/me`,
      loginUrl: `${devData.authUrl}/login`   // login by routing seems to not work currently, TODO: #48 @ trello
    },
    tournament: {
      url: devData.serverUrl,
      saveUrl: `${devData.serverUrl}/tournament/api/save`
    },
    api: {
      url: `${devData.serverUrl}`
    },
  },
  client: {
    loginUrl: '/login',
    dashboard: {
      url: '/dashboard',
      mainPanelUrl: '/dashboard/main',
      activeTournamentsPanelUrl: '/dashboard/active',
      managementPanelUrl: '/dashboard/management',
      adminPanelUrl: '/dashboard/admin',
      developmentPanelUrl: '/dashboard/development'
    }
  }
};
