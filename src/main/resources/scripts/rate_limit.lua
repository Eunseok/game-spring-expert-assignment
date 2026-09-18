-- KEYS[1] = 카운트 키 (예: "chat:limit:123")
-- ARGV[1] = 최대 허용 횟수 (예: 5)
-- ARGV[2] = 만료 시간(초) (예: 10)

local current = redis.call('GET', KEYS[1])

if current and tonumber(current) >= tonumber(ARGV[1]) then
    return 0  -- 제한 초과, 거부
end

local updated = redis.call('INCR', KEYS[1])

if updated == 1 then
    redis.call('EXPIRE', KEYS[1], tonumber(ARGV[2]))
end

return 1  -- 허용