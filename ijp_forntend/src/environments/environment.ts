// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  //baseUrl: 'https://tekg.arigs.com/ijpservices',
  //authUrl :'https://tekg.arigs.com/ijpservices',
  //logoutUrl : "https://login.microsoftonline.com/organizations/oauth2/v2.0/logout?post_logout_redirect_uri=https://tekg.arigs.com/ijp/"

  // baseUrl: 'http://localhost/ijpservices',
  // authUrl :'http://localhost/ijpservices',
  // logoutUrl : "https://login.microsoftonline.com/organizations/oauth2/v2.0/logout?post_logout_redirect_uri=http://localhost/ijp/"


  baseUrl: 'http://localhost:8080/ijpservices',
  authUrl :'http://localhost:8080',
  logoutUrl : "https://login.microsoftonline.com/organizations/oauth2/v2.0/logout?post_logout_redirect_uri=http://localhost:42000/ijp"

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
