import {Component, OnInit} from '@angular/core';
import {ConfigItem} from "../../../../interfaces/config-item";
import {SettingsService} from "../../../../services/settings.service";
import {AlertService} from "../../../../_alert";
import {TitleService} from "../../../../services/title.service";
import {CatalogService} from "../../../../services/catalog.service";
import {Catalog} from "../../../../interfaces/catalog";

@Component({
    selector: 'app-list-settings',
    templateUrl: './list-settings.component.html',
    styleUrls: ['./list-settings.component.scss']
})
export class ListSettingsComponent implements OnInit {

    items: ConfigItem[] = [];
    categories: Catalog[] = [];
    priorities: Catalog[] = [];
    statuses: Catalog[] = [];

    email: string = '';
    password: string = '';
    server: string = '';
    acl: string = '';
    category: Catalog | undefined;
    priority: Catalog | undefined;
    status: Catalog | undefined;
    enableAutoFetch: boolean = false;

    constructor(
        private settingsService: SettingsService,
        private catalogService: CatalogService,
        private alertService: AlertService,
        private titleService: TitleService
    ) {
    }

    ngOnInit(): void {
        this.titleService.showTitleMsg('System settings', '', false);
        this.settingsService.getAllSettings().subscribe(items => {
            this.items = items;

            this.email = this.items.find(it => {
                return it.name === 'email'
            })!.value;

            this.password = this.items.find(it => {
                return it.name === 'password'
            })!.value;

            this.server = this.items.find(it => {
                return it.name === 'mailServer'
            })!.value;

            this.enableAutoFetch = Boolean(this.items.find(it => {
                return it.name === 'auto_fetch_email'
            })!.value);

            this.acl = this.items.find(it => {
                return it.name === 'default_acl'
            })!.value;

            this.catalogService.getCatalogItemList('category').subscribe(cat => {
                this.categories = cat;
                this.category = this.categories.find(cat => {
                    return cat.id === parseInt(this.items.find(it => {
                        return it.name === 'default_cat'
                    })!.value);
                })
                console.log('cat', this.category);
            });

            this.catalogService.getCatalogItemList('priority').subscribe(pri => {
                this.priorities = pri;
                this.priority = this.priorities.find(pri => {
                    return pri.id === parseInt(this.items.find(it => {
                        return it.name === 'default_priority'
                    })!.value);
                })
            });

            this.catalogService.getCatalogItemList('status').subscribe(st => {
                this.statuses = st;
                this.status = this.statuses.find(st => {
                    return st.id === parseInt(this.items.find(it => {
                        return it.name === 'default_status'
                    })!.value);
                })
            });


        });


    }

    updateSettingItem($event: any, name: string) {
        console.log('event', $event);
        const item: ConfigItem | undefined = this.items.find(it => {
            return it.name === name;
        });
        if(item) {
            item.value = $event;
            this.settingsService.updateSetting(item).subscribe(data => {
                this.alertService.success(data.result, { autoClose: true});
            });
        }
    }
}
