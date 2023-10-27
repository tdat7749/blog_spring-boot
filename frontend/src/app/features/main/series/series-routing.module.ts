import {RouterModule, Routes} from "@angular/router";
import {SeriesComponent} from "./series.component";
import {NgModule} from "@angular/core";
import {SeriesDetailComponent} from "./series-detail/series-detail.component";

const routes:Routes = [
    {
        path:"",
        component:SeriesComponent
    },
    {
        path:":slug",
        component:SeriesDetailComponent
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class SeriesRoutingModule {}