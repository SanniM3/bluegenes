(ns bluegenes.views
  (:require [re-frame.core :as re-frame :refer [subscribe]]
            [bluegenes.pages.developer.devhome :as dev]
            [bluegenes.components.navbar.nav :as nav]
            [bluegenes.components.footer.views :as footer]
            [bluegenes.components.icons :as icons]
            [bluegenes.pages.home.views :as home]
            [bluegenes.components.search.views :as search]
            [bluegenes.effects]
            [bluegenes.pages.reportpage.views :as reportpage]
            [bluegenes.pages.templates.views :as templates]
            [bluegenes.pages.querybuilder.views :as qb]
            [bluegenes.pages.lists.views :as lists]
            [bluegenes.components.ui.alerts :as alerts]
            [bluegenes.pages.upload.views :as upload]
            [bluegenes.pages.upgrade.views :as upgrade]
            [bluegenes.pages.results.views :as results]
            [bluegenes.pages.regions.views :as regions]
            [bluegenes.pages.profile.views :as profile]
            [bluegenes.pages.admin.views :as admin]
            [bluegenes.pages.tools.view :as tools]
            [bluegenes.components.loader :as loader]
            [bluegenes.error :refer [error-boundary]]))

(enable-console-print!)

(defn show-panel [panel-name]
  [(case panel-name
     :home-panel         home/main
     :admin-panel        admin/main
     :profile-panel      profile/main
     :debug-panel        dev/debug-panel
     :tools-panel        tools/main
     :templates-panel    templates/main
     :reportpage-panel   reportpage/main
     :upload-panel       upload/main
     :upgrade-panel      upgrade/main
     :search-panel       search/main
     :results-panel      results/main
     :regions-panel      regions/main
     :lists-panel        lists/main
     :querybuilder-panel qb/main
     home/main)])

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])
        main-color (subscribe [:branding/header-main])
        text-color (subscribe [:branding/header-text])]
    (fn []
      [error-boundary
       [:div.approot
        {:style {"--branding-header-main" @main-color
                 "--branding-header-text" @text-color}}
        [loader/mine-loader]
        [icons/icons]
        [nav/main]
        [:main [show-panel @active-panel]]
        [footer/main]
        [alerts/main]]])))
