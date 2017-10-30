const devData = {
  serverUrl: 'http://localhost:8080',
  authUrl:  'http://localhost:8090'
}

export const environment = {
  production: true,
  authToken: 'oauth-token',
  githubUrl: 'http://github.com',
  server: {
    auth: {
      logoutUrl: `${devData.serverUrl}/auth/logout`,
      logoutGatewayUrl: `${devData.serverUrl}/auth/api/logout`,
      userUrl: `${devData.serverUrl}/auth/api/user`,
      meUrl:  `${devData.serverUrl}/auth/api/me`,
      loginUrl: `${devData.authUrl}/login`,   // login by routing seems to not work currently, TODO: #48 @ trello
      adminUrl: `${devData.serverUrl}/auth/admin`
    },
    tournament: {
      allTournamentsUrl: `${devData.serverUrl}/tournament/api/all`,
      managementTournamentsUrl: `${devData.serverUrl}/tournament/api/management`,
      userDetailsTournamentUrl: `${devData.serverUrl}/tournament/api/user-details`,
      userActionTournamentUrl: `${devData.serverUrl}/tournament/api/user-action`,
      saveUrl: `${devData.serverUrl}/tournament/api/management/save`,
      submitTaskUrl: `${devData.serverUrl}/tournament/api/task/submit`,
      adminUrl: `${devData.serverUrl}/tournament/admin`
    },
    tournamentResults: {
      taskUserDetailsUrl: `${devData.serverUrl}/results/api/user-details`
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
