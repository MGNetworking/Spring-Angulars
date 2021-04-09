import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

/** Lancement du module de l'application */
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
