import { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

const NODES = [
  { id: 1, role: 'Data Engineer', company: 'WSFS Bank', location: 'Wilmington, DE', skills: 'Java, SQL, Python, AWS', openings: 142 },
  { id: 2, role: 'ML Engineer', company: 'Anthropic', location: 'Remote', skills: 'Python, TensorFlow, LLMs', openings: 38 },
  { id: 3, role: 'Backend Engineer', company: 'JPMorgan', location: 'Philadelphia, PA', skills: 'Java, Spring Boot, SQL', openings: 89 },
  { id: 4, role: 'Data Analyst', company: 'Comcast', location: 'Philadelphia, PA', skills: 'SQL, Python, Tableau', openings: 67 },
  { id: 5, role: 'DevOps Engineer', company: 'Sallie Mae', location: 'Newark, DE', skills: 'AWS, Docker, Kubernetes', openings: 45 },
  { id: 6, role: 'AI Engineer', company: 'DuPont', location: 'Wilmington, DE', skills: 'Python, LLMs, APIs', openings: 29 },
  { id: 7, role: 'Full Stack Dev', company: 'Incyte', location: 'Wilmington, DE', skills: 'React, Node.js, SQL', openings: 54 },
  { id: 8, role: 'Cloud Architect', company: 'Siemens', location: 'Philadelphia, PA', skills: 'AWS, Azure, Terraform', openings: 33 },
]

export default function LandingPage() {
  const canvasRef = useRef(null)
  const navigate = useNavigate()
  const [expandedNode, setExpandedNode] = useState(null)
  const [nodePositions, setNodePositions] = useState([])
  const mouseRef = useRef({ x: -999, y: -999 })
  const animRef = useRef(null)
  const particlesRef = useRef([])
  const nodesRef = useRef([])

  useEffect(() => {
    const canvas = canvasRef.current
    const ctx = canvas.getContext('2d')
    let W = canvas.width = window.innerWidth
    let H = canvas.height = window.innerHeight

    const handleResize = () => {
      W = canvas.width = window.innerWidth
      H = canvas.height = window.innerHeight
      initNodes()
    }

    const handleMouseMove = (e) => {
      mouseRef.current = { x: e.clientX, y: e.clientY }
    }

    const initParticles = () => {
      particlesRef.current = []
      const count = Math.floor((W * H) / 9000)
      for (let i = 0; i < count; i++) {
        particlesRef.current.push({
          x: Math.random() * W,
          y: Math.random() * H,
          vx: (Math.random() - 0.5) * 0.25,
          vy: (Math.random() - 0.5) * 0.25,
          r: Math.random() * 1.0 + 0.3,
        })
      }
    }

    const initNodes = () => {
      const centerX = W / 2
      const centerY = H / 2

      nodesRef.current = NODES.map((node) => ({
        ...node,
        x: Math.random() * (W - 200) + 100,
        y: Math.random() * (H - 200) + 100,
        vx: (Math.random() - 0.5) * 0.5,
        vy: (Math.random() - 0.5) * 0.5,
        r: 13,
        isYou: false,
      }))

      nodesRef.current.push({
        id: 0,
        role: 'YOU',
        x: centerX,
        y: centerY,
        baseX: centerX,
        baseY: centerY,
        vx: 0,
        vy: 0,
        r: 18,
        isYou: true,
      })

      setNodePositions(nodesRef.current.map(n => ({
        id: n.id, x: n.x, y: n.y, r: n.r, isYou: n.isYou, role: n.role
      })))
    }

    let t = 0
    const draw = () => {
      ctx.clearRect(0, 0, W, H)
      t += 0.016

      // Radial glow from center
      ctx.save()
      const gradient = ctx.createRadialGradient(W/2, H/2, 0, W/2, H/2, Math.max(W,H) * 0.7)
      gradient.addColorStop(0, 'rgba(0,210,255,0.05)')
      gradient.addColorStop(1, 'rgba(0,210,255,0)')
      ctx.fillStyle = gradient
      ctx.fillRect(0, 0, W, H)
      ctx.restore()

      // Radar scan
      const scanAngle = (t * 0.4) % (Math.PI * 2)
      ctx.save()
      ctx.translate(W/2, H/2)
      ctx.rotate(scanAngle)
      const scanLen = Math.max(W, H)
      const scanGrad = ctx.createLinearGradient(0, 0, scanLen, 0)
      scanGrad.addColorStop(0, 'rgba(0,210,255,0.1)')
      scanGrad.addColorStop(1, 'rgba(0,210,255,0)')
      ctx.beginPath()
      ctx.moveTo(0, 0)
      ctx.arc(0, 0, scanLen, -0.12, 0.12)
      ctx.closePath()
      ctx.fillStyle = scanGrad
      ctx.fill()
      ctx.restore()

      // Radial grid circles
      for (let i = 1; i <= 5; i++) {
        ctx.beginPath()
        ctx.arc(W/2, H/2, (Math.min(W,H) * 0.15) * i, 0, Math.PI * 2)
        ctx.strokeStyle = 'rgba(10,58,90,0.2)'
        ctx.lineWidth = 0.5
        ctx.stroke()
      }

      // Radial grid lines
      for (let i = 0; i < 12; i++) {
        const angle = (i / 12) * Math.PI * 2
        ctx.beginPath()
        ctx.moveTo(W/2, H/2)
        ctx.lineTo(W/2 + Math.cos(angle) * Math.max(W,H), H/2 + Math.sin(angle) * Math.max(W,H))
        ctx.strokeStyle = 'rgba(10,58,90,0.12)'
        ctx.lineWidth = 0.5
        ctx.stroke()
      }

      // Background particles
      particlesRef.current.forEach(p => {
        p.x += p.vx; p.y += p.vy
        if (p.x < 0) p.x = W; if (p.x > W) p.x = 0
        if (p.y < 0) p.y = H; if (p.y > H) p.y = 0
        ctx.beginPath()
        ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2)
        ctx.fillStyle = 'rgba(0,210,255,0.12)'
        ctx.fill()
      })

      // Float job nodes freely — YOU stays fixed at center
      const margin = 100
      nodesRef.current.forEach(node => {
        if (node.isYou) {
          node.x = W / 2
          node.y = H / 2
          return
        }

        node.x += node.vx
        node.y += node.vy

        // Boundary push
        if (node.x < margin) node.vx += 0.12
        if (node.x > W - margin) node.vx -= 0.12
        if (node.y < H * 0.28) node.vy += 0.12
        if (node.y > H - margin) node.vy -= 0.12

        // Speed limit — calm drift
        const speed = Math.sqrt(node.vx * node.vx + node.vy * node.vy)
        if (speed > 0.6) {
          node.vx = (node.vx / speed) * 0.6
          node.vy = (node.vy / speed) * 0.6
        }
      })

      // Find hovered node
      let hoveredId = null
      nodesRef.current.forEach(node => {
        const dx = mouseRef.current.x - node.x
        const dy = mouseRef.current.y - node.y
        const dist = Math.sqrt(dx*dx + dy*dy)
        if (dist < 50) hoveredId = node.id
      })

      // Draw ALL connections — full mesh network
      const allNodes = nodesRef.current
      for (let i = 0; i < allNodes.length; i++) {
        for (let j = i + 1; j < allNodes.length; j++) {
          const a = allNodes[i]
          const b = allNodes[j]
          const dx = a.x - b.x
          const dy = a.y - b.y
          const dist = Math.sqrt(dx*dx + dy*dy)

          // Only draw connections within range
          const maxDist = 320
          if (dist > maxDist) continue

          const isHoveredConnection = hoveredId === a.id || hoveredId === b.id
          const isYouConnection = a.isYou || b.isYou
          const alpha = isHoveredConnection
            ? 0.55 + Math.sin(t * 6) * 0.15
            : isYouConnection
            ? 0.2
            : (1 - dist / maxDist) * 0.12
          const lineW = isHoveredConnection ? 1.4 : isYouConnection ? 0.8 : 0.4

          ctx.beginPath()
          ctx.moveTo(a.x, a.y)
          ctx.lineTo(b.x, b.y)
          ctx.strokeStyle = `rgba(0,210,255,${alpha})`
          ctx.lineWidth = lineW
          ctx.stroke()

          // Traveling dot on hovered connection
          if (isHoveredConnection && dist < maxDist) {
            const progress = (t * 0.6) % 1
            const dotX = a.x + (b.x - a.x) * progress
            const dotY = a.y + (b.y - a.y) * progress
            ctx.beginPath()
            ctx.arc(dotX, dotY, 2.5, 0, Math.PI * 2)
            ctx.fillStyle = '#00ffcc'
            ctx.fill()
          }
        }
      }

      // Draw nodes
      nodesRef.current.forEach(node => {
        const isHovered = hoveredId === node.id

        // Glow
        const glowSize = node.isYou ? 35 : isHovered ? 28 : 14
        const glowAlpha = node.isYou ? 0.35 : isHovered ? 0.3 : 0.08
        const glowGrad = ctx.createRadialGradient(node.x, node.y, 0, node.x, node.y, glowSize)
        glowGrad.addColorStop(0, node.isYou ? `rgba(196,114,240,${glowAlpha})` : `rgba(0,210,255,${glowAlpha})`)
        glowGrad.addColorStop(1, 'rgba(0,0,0,0)')
        ctx.beginPath()
        ctx.arc(node.x, node.y, glowSize, 0, Math.PI * 2)
        ctx.fillStyle = glowGrad
        ctx.fill()

        // Node circle
        const nodeR = node.isYou ? 18 + Math.sin(t * 2) * 2 : node.r
        ctx.beginPath()
        ctx.arc(node.x, node.y, nodeR, 0, Math.PI * 2)
        ctx.fillStyle = node.isYou
          ? 'rgba(196,114,240,0.3)'
          : isHovered ? 'rgba(0,255,204,0.18)' : 'rgba(0,210,255,0.1)'
        ctx.strokeStyle = node.isYou ? '#c472f0' : isHovered ? '#00ffcc' : '#00d2ff'
        ctx.lineWidth = node.isYou ? 2.5 : isHovered ? 2 : 1.2
        ctx.fill()
        ctx.stroke()

        // Role label
        if (!node.isYou) {
          ctx.font = `${isHovered ? '600' : '500'} 10px "Space Grotesk", sans-serif`
          ctx.fillStyle = isHovered ? 'rgba(0,255,204,0.95)' : 'rgba(0,210,255,0.55)'
          ctx.textAlign = 'center'
          ctx.fillText(node.role, node.x, node.y + node.r + 15)
        }
      })

      setNodePositions(nodesRef.current.map(n => ({
  id: n.id,
  x: n.x,
  y: n.y,
  r: n.r,
  isYou: n.isYou,
  role: n.role,
  company: n.company,
  location: n.location,
  skills: n.skills,
  openings: n.openings,
})))

      animRef.current = requestAnimationFrame(draw)
    }

    initParticles()
    initNodes()
    draw()

    window.addEventListener('resize', handleResize)
    window.addEventListener('mousemove', handleMouseMove)

    return () => {
      cancelAnimationFrame(animRef.current)
      window.removeEventListener('resize', handleResize)
      window.removeEventListener('mousemove', handleMouseMove)
    }
  }, [])

  const handleNodeClick = (node) => {
    if (node.isYou) return
    setExpandedNode(expandedNode?.id === node.id ? null : node)
  }

  return (
    <div style={{ position: 'relative', width: '100vw', height: '100vh', overflow: 'hidden', background: '#020d1a' }}>

      <canvas ref={canvasRef} style={{ position: 'absolute', top: 0, left: 0, zIndex: 1 }} />

      {/* Clickable node overlays */}
      {nodePositions.map(node => (
        <div
          key={node.id}
          onClick={() => handleNodeClick(node)}
          style={{
            position: 'absolute',
            left: node.x - (node.isYou ? 18 : 13),
            top: node.y - (node.isYou ? 18 : 13),
            width: node.isYou ? 36 : 26,
            height: node.isYou ? 36 : 26,
            borderRadius: '50%',
            cursor: node.isYou ? 'default' : 'pointer',
            zIndex: 3,
          }}
        />
      ))}

      {/* YOU label */}
      {nodePositions.find(n => n.isYou) && (
        <div style={{
          position: 'absolute',
          left: nodePositions.find(n => n.isYou).x,
          top: nodePositions.find(n => n.isYou).y - 32,
          transform: 'translateX(-50%)',
          color: '#c472f0',
          fontSize: '11px',
          fontFamily: 'Space Grotesk, sans-serif',
          fontWeight: 600,
          letterSpacing: '0.15em',
          zIndex: 4,
          pointerEvents: 'none',
        }}>YOU</div>
      )}

      {/* Expanded job card */}
      {expandedNode && (
        <div style={{
          position: 'absolute',
          left: Math.min(expandedNode.x + 24, window.innerWidth - 280),
          top: Math.min(expandedNode.y - 60, window.innerHeight - 220),
          width: 260,
          background: 'rgba(2,13,26,0.96)',
          border: '1px solid rgba(0,210,255,0.4)',
          borderRadius: 12,
          padding: '1rem',
          zIndex: 10,
          backdropFilter: 'blur(12px)',
        }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
  <p style={{ color: '#00d2ff', fontWeight: 600, fontSize: 14, fontFamily: 'Syne, sans-serif', margin: 0 }}>{expandedNode.role}</p>
  <button onClick={() => setExpandedNode(null)} style={{ background: 'transparent', border: 'none', color: 'rgba(255,255,255,0.4)', fontSize: 16, cursor: 'pointer', padding: '0 0 0 8px', lineHeight: 1 }}>✕</button>
</div>
          <p style={{ color: 'rgba(255,255,255,0.7)', fontSize: 12, margin: '0 0 4px', fontFamily: 'Space Grotesk, sans-serif' }}>{expandedNode.company} · {expandedNode.location}</p>
          <p style={{ color: 'rgba(255,255,255,0.5)', fontSize: 11, margin: '0 0 8px', fontFamily: 'Space Grotesk, sans-serif' }}>{expandedNode.skills}</p>
          <p style={{ color: '#00ffcc', fontSize: 11, margin: '0 0 12px', fontFamily: 'Space Grotesk, sans-serif' }}>● {expandedNode.openings} openings</p>
          <button
            onClick={() => navigate('/register')}
            style={{
              width: '100%', padding: '7px', background: 'transparent',
              border: '1px solid rgba(0,210,255,0.5)', borderRadius: 6,
              color: '#00d2ff', fontSize: 12, cursor: 'pointer',
              fontFamily: 'Space Grotesk, sans-serif', fontWeight: 500,
            }}>
            Find your path →
          </button>
        </div>
      )}

      {/* Hero text */}
      <div style={{
        position: 'absolute',
        top: '12%',
        left: '50%',
        transform: 'translateX(-50%)',
        textAlign: 'center',
        zIndex: 5,
        pointerEvents: 'none',
      }}>
        <h1 style={{
          fontFamily: 'Syne, sans-serif',
          fontSize: 'clamp(52px, 8vw, 100px)',
          fontWeight: 800,
          color: '#ffffff',
          letterSpacing: '0.08em',
          margin: 0,
          lineHeight: 1,
          textShadow: '0 0 40px rgba(0,210,255,0.8), 0 0 80px rgba(0,210,255,0.4), 0 0 120px rgba(0,210,255,0.2)',
        }}>NODUS</h1>
        <p style={{
          fontFamily: 'Space Grotesk, sans-serif',
          fontSize: 'clamp(12px, 1.4vw, 15px)',
          color: 'rgba(255,255,255,0.45)',
          letterSpacing: '0.22em',
          margin: '14px 0 0',
          textTransform: 'uppercase',
        }}>One platform. Every connection you need.</p>
      </div>

      {/* CTA buttons */}
      <div style={{
        position: 'absolute',
        bottom: '7%',
        left: '50%',
        transform: 'translateX(-50%)',
        display: 'flex',
        gap: '16px',
        zIndex: 5,
      }}>
        <button
          onClick={() => navigate('/register')}
          style={{
            padding: '12px 32px',
            background: 'transparent',
            border: '1px solid rgba(0,210,255,0.6)',
            borderRadius: 24,
            color: '#00d2ff',
            fontSize: 14,
            fontFamily: 'Space Grotesk, sans-serif',
            fontWeight: 600,
            cursor: 'pointer',
            letterSpacing: '0.06em',
          }}
          onMouseEnter={e => e.target.style.background = 'rgba(0,210,255,0.1)'}
          onMouseLeave={e => e.target.style.background = 'transparent'}
        >
          Find Your Path
        </button>
        <button
          onClick={() => navigate('/register')}
          style={{
            padding: '12px 32px',
            background: 'transparent',
            border: '1px solid rgba(255,255,255,0.2)',
            borderRadius: 24,
            color: 'rgba(255,255,255,0.65)',
            fontSize: 14,
            fontFamily: 'Space Grotesk, sans-serif',
            fontWeight: 600,
            cursor: 'pointer',
            letterSpacing: '0.06em',
          }}
          onMouseEnter={e => e.target.style.background = 'rgba(255,255,255,0.05)'}
          onMouseLeave={e => e.target.style.background = 'transparent'}
        >
          Post an Opportunity
        </button>
      </div>

    </div>
  )
}