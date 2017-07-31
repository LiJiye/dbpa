namespace java com.lijiye.dbpa.thrift
struct MatchDetail{
    1: list<i16>  winners,
    2: list<i16> losers

}
service DbpaService {
    i16 add(1: list<MatchDetail> matchDdetails)
}