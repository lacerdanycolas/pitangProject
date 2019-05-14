export interface TvSerie{
    id:number,
    name:string,
    overview:string,
    original_language:string,
    first_air_date:string,
    episode_run_time:number,
    backdrop_path:string,
    genres_id:Array<number>,
    original_countries:Array<string>
}