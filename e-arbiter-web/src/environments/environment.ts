// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses 'environment.ts', but if you do
// 'ng build --env=prod' then 'environment.prod.ts' will be used instead.
// The list of which env maps to which file can be found in '.angular-cli.json'.

export const environment = {
  production: false,
  authToken: 'oauth_token',
  githubUrl: 'http://github.com',
  server: {
    auth: {
      url: 'http://localhost:8090',
      logoutUrl: 'http://localhost:8080/auth/logout',
      userUrl: 'http://localhost:8080/auth/api/user',
      tokenUrl: 'http://localhost:8080/auth/api/token',
      meUrl:  'http://localhost:8080/auth/api/me',
      loginUrl: 'http://localhost:8090/login'   // login by routing seems to not work currently, TODO: #48 @ trello
    },
    api: {
      url: 'http://localhost:8080'
    },
  },
  client: {
    mainUrl: '/main',
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
