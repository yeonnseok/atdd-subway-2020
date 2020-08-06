import ApiService from '@/api'

const PathService = {
  get(source, target, type, time) {
    return ApiService.get(`/paths?source=${source}&target=${target}&type=${type}&time=${time}`)
  }
}

export default PathService
