// @ts-nocheck
import React from 'react';
import { ApplyPluginsType, dynamic } from 'D:/WebCode/DOIT-WEBAPP-master/node_modules/@umijs/runtime';
import * as umiExports from './umiExports';
import { plugin } from './plugin';
import LoadingComponent from '@/components/PageLoading/index';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__BlankLayout' */'D:/WebCode/DOIT-WEBAPP-master/src/layouts/BlankLayout'), loading: LoadingComponent}),
    "routes": [
      {
        "path": "/user",
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__UserLayout' */'D:/WebCode/DOIT-WEBAPP-master/src/layouts/UserLayout'), loading: LoadingComponent}),
        "routes": [
          {
            "path": "/user/login",
            "name": "login",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__user__login' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/user/login'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "path": "/user",
            "redirect": "/user/login",
            "exact": true
          },
          {
            "name": "register-result",
            "icon": "smile",
            "path": "/user/register-result",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__user__register-result' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/user/register-result'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "name": "register",
            "icon": "smile",
            "path": "/user/register",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__user__register' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/user/register'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__404' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/404'), loading: LoadingComponent}),
            "exact": true
          }
        ]
      },
      {
        "path": "/",
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__BasicLayout' */'D:/WebCode/DOIT-WEBAPP-master/src/layouts/BasicLayout'), loading: LoadingComponent}),
        "Routes": [
          "src/pages/Authorized"
        ],
        "authority": [
          "admin",
          "user"
        ],
        "routes": [
          {
            "path": "/",
            "redirect": "/rulemarketing/ruledashboard",
            "exact": true
          },
          {
            "name": "rulemarketing",
            "path": "/rulemarketing",
            "icon": "smile",
            "routes": [
              {
                "path": "/",
                "redirect": "/rulemarketing/ruledefine",
                "exact": true
              },
              {
                "name": "ruledefine",
                "icon": "smile",
                "path": "/rulemarketing/ruledefine",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__rulemarketing__ruledefine' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/rulemarketing/ruledefine'), loading: LoadingComponent}),
                "exact": true
              },
              {
                "name": "ruledashboard",
                "icon": "smile",
                "path": "/rulemarketing/ruledashboard",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__rulemarketing__ruledashboard' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/rulemarketing/ruledashboard'), loading: LoadingComponent}),
                "exact": true
              },
              {
                "name": "rulemonitoring",
                "icon": "smile",
                "path": "/rulemarketing/rulemonitoring",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__rulemarketing__rulemonitoring' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/rulemarketing/rulemonitoring'), loading: LoadingComponent}),
                "exact": true
              }
            ]
          },
          {
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__404' */'D:/WebCode/DOIT-WEBAPP-master/src/pages/404'), loading: LoadingComponent}),
            "exact": true
          }
        ]
      }
    ]
  }
];

  // allow user to extend routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });

  return routes;
}
