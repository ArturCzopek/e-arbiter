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
      logoutUrl: 'http://localhost:8090/logout',
      userUrl: 'http://localhost:8090/api/user',
      tokenUrl: 'http://localhost:8090/api/token',
      loginUrl: 'http://localhost:8090/login'
    },
    proxy: {
      url: 'http://localhost:8080'
    },
  },
  client: {
    mainUrl: '/main',
    dashboard: {
      url: '/dashboard',
      dataUrl: '/dashboard/data'
    }
  }
};
