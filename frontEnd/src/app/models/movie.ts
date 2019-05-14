export interface Movie {
    id:number,
    title:string,
    backdrop_path:string,
    overview:string,
    original_language:string,
    release_date:string,
    runtime:number,
    genres_id:Array<number>
}